package ua.telegrambot.botapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import ua.telegrambot.CryptoBot;
import ua.telegrambot.botapi.model.UserSubscription;
import ua.telegrambot.cache.UserDataCache;
import ua.telegrambot.service.*;
import ua.telegrambot.utils.Emojis;

import javax.swing.text.html.Option;

import java.util.Optional;

import static ua.telegrambot.botapi.BotState.*;
import static ua.telegrambot.botapi.Currencies.*;

@Component
@Slf4j
public class TelegramFacade {
	private BotStateContext botStateContext;
	private UserDataCache userDataCache;
	private MainMenuService mainMenuService;
	private CryptoBot cryptoBot;
	private ReplyMessageService messageService;
	private CurrencySubscriptionService currencySubscriptionService;
	private ParserHTMLPriceService parserService;
	private UserSubscriptionService subscriptionService;

	public TelegramFacade(BotStateContext botStateContext,
	                      UserDataCache userDataCache,
	                      MainMenuService mainMenuService,
	                      @Lazy CryptoBot cryptoBot,
	                      ReplyMessageService messageService,
	                      CurrencySubscriptionService currencySubscriptionService,
	                      ParserHTMLPriceService parserService,
	                      UserSubscriptionService subscriptionService) {
		this.botStateContext = botStateContext;
		this.userDataCache = userDataCache;
		this.mainMenuService = mainMenuService;
		this.cryptoBot = cryptoBot;
		this.messageService = messageService;
		this.currencySubscriptionService = currencySubscriptionService;
		this.parserService = parserService;
		this.subscriptionService = subscriptionService;
	}

	public BotApiMethod<?> handleUpdate(Update update){
		SendMessage replyMessage = null;

		if (update.hasCallbackQuery()){
			CallbackQuery callbackQuery = update.getCallbackQuery();
			log.info("New callBackQuery from User:{}, userId:{}, with data:{}",update.getCallbackQuery().getFrom().getUserName(),
					update.getCallbackQuery().getFrom().getId(),update.getCallbackQuery().getData());
			return processCallBackQuery(callbackQuery);
		}

		Message message = update.getMessage();
		if (message != null && message.hasText()){
			log.info("New message from User:{}, chatId:{}, with text:{}",message.getFrom().getUserName(),message.getChatId(),message.getText());
			replyMessage = handleInputMessage(message);
		}

		return replyMessage;
	}

	private SendMessage handleInputMessage(Message message) {
		String inputMsg = message.getText();
		int userId = message.getFrom().getId();
		String chat_id = message.getChatId().toString();
		BotState botState;
		SendMessage replyMessage;

		switch (inputMsg){
			case "/start":
				botState = SHOW_MAIN_MENU;
				cryptoBot.sendPhoto(chat_id,messageService.getReplyText("reply.hello"), "static/images/coin-image.jpg");
				break;
			case "Просмотреть цены криптовалют":
				botState = SHOW_CRYPTO_SEARCH;
				break;
			case "Список подписок":
				botState = SHOW_SUBSCRIPTIONS;
				break;
			case "Подписаться на криптовалюту":
				botState = FILLING_PROFILE;
				break;
			case "Помощь":
				botState = SHOW_HELP_MENU;
				break;
			case "Что такое криптовалюта?":
				botState = SHOW_CRYPTO_INFORMATION;
				break;
			default:
				botState = SHOW_MAIN_MENU;
				break;
		}

		userDataCache.setUsersCurrentBotState(userId,botState);

		replyMessage = botStateContext.processInputMessage(botState,message);

		return replyMessage;
	}

	private BotApiMethod<?> processCallBackQuery(CallbackQuery callbackQuery){
		final String chat_id = callbackQuery.getMessage().getChatId().toString();
		final int user_id = callbackQuery.getFrom().getId();
		BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chat_id,"reply.menu");


		UserSubscription userSubscription = userDataCache.getUserProfileData(user_id);



		// From asking price
		if (callbackQuery.getData().equals("BitcoinPrice")){
			cryptoBot.sendMessage(chat_id,parserService.processInputMessage(BITCOIN));
			callBackAnswer = messageService.getReplyMessage(chat_id,"reply.gratitude",Emojis.TADA);
		} else if (callbackQuery.getData().equals("LitecoinPrice")){
			cryptoBot.sendMessage(chat_id,parserService.processInputMessage(LITECOIN));
			callBackAnswer = messageService.getReplyMessage(chat_id,"reply.gratitude",Emojis.TADA);
		} else if(callbackQuery.getData().equals("DogecoinPrice")){
			cryptoBot.sendMessage(chat_id,parserService.processInputMessage(DOGECOIN));
			callBackAnswer = messageService.getReplyMessage(chat_id,"reply.gratitude",Emojis.TADA);
		} else if (callbackQuery.getData().equals("EthereumPrice")){
			cryptoBot.sendMessage(chat_id,parserService.processInputMessage(ETHEREUM));
			callBackAnswer = messageService.getReplyMessage(chat_id,"reply.gratitude",Emojis.TADA);
		}


		// From subscribing
		else if (callbackQuery.getData().equals("BitcoinSubscribe") && userSubscription.getBitcoin() != BITCOIN){
			currencySubscriptionService.subscribeUserCurrency(BITCOIN,callbackQuery);
			callBackAnswer = messageService.getReplyMessage(chat_id,"reply.bitcoin",Emojis.SPARKLES);
		} else if (callbackQuery.getData().equals("LitecoinSubscribe") && userSubscription.getLitecoin() != LITECOIN){
			currencySubscriptionService.subscribeUserCurrency(LITECOIN,callbackQuery);
			callBackAnswer = messageService.getReplyMessage(chat_id,"reply.litecoin",Emojis.SPARKLES);
		} else if(callbackQuery.getData().equals("DogecoinSubscribe") && userSubscription.getDogecoin() != DOGECOIN){
			currencySubscriptionService.subscribeUserCurrency(DOGECOIN,callbackQuery);
			callBackAnswer = messageService.getReplyMessage(chat_id,"reply.dogecoin",Emojis.SPARKLES);
		} else if (callbackQuery.getData().equals("EthereumSubscribe") && userSubscription.getEthereum() != ETHEREUM){
			currencySubscriptionService.subscribeUserCurrency(ETHEREUM,callbackQuery);
			callBackAnswer = messageService.getReplyMessage(chat_id,"reply.ethereum",Emojis.SPARKLES);
		}

		//From unsubscribing
		else if (callbackQuery.getData().equals("BitcoinSubscribe") && userSubscription.getBitcoin() == BITCOIN){
			currencySubscriptionService.unsubscribeUserCurrency(BITCOIN,callbackQuery);
			callBackAnswer = sendAnswerCallbackQuery("Вы успешно отписались от Bitcoin",true,callbackQuery);
		} else if (callbackQuery.getData().equals("LitecoinSubscribe") && userSubscription.getLitecoin() == LITECOIN){
			currencySubscriptionService.unsubscribeUserCurrency(LITECOIN,callbackQuery);
			callBackAnswer = sendAnswerCallbackQuery("Вы успешно отписались от Litecoin",true,callbackQuery);
		} else if(callbackQuery.getData().equals("DogecoinSubscribe") && userSubscription.getDogecoin() == DOGECOIN){
			currencySubscriptionService.unsubscribeUserCurrency(DOGECOIN,callbackQuery);
			callBackAnswer = sendAnswerCallbackQuery("Вы успешно отписались от Dogecoin",true,callbackQuery);
		} else if (callbackQuery.getData().equals("EthereumSubscribe") && userSubscription.getEthereum() == ETHEREUM){
			currencySubscriptionService.unsubscribeUserCurrency(ETHEREUM,callbackQuery);
			callBackAnswer = sendAnswerCallbackQuery("Вы успешно отписались от Ethereum",true,callbackQuery);
		} else {
			callBackAnswer = messageService.getReplyMessage(chat_id,"reply.query.error");
		}

		return callBackAnswer;
	}

	private AnswerCallbackQuery sendAnswerCallbackQuery(String text,boolean alert,CallbackQuery callbackQuery){
		AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
		answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
		answerCallbackQuery.setText(text);
		answerCallbackQuery.setShowAlert(alert);
		return answerCallbackQuery;
	}
}
