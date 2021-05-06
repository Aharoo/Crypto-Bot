package ua.telegrambot.botapi.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.telegrambot.CryptoBot;
import ua.telegrambot.botapi.BotState;
import ua.telegrambot.botapi.InputMessageHandler;
import ua.telegrambot.botapi.model.UserSubscription;
import ua.telegrambot.service.ReplyMessageService;
import ua.telegrambot.service.UserSubscriptionService;
import ua.telegrambot.utils.Emojis;
import java.util.Optional;
import static ua.telegrambot.botapi.Currencies.*;

@Component
public class ShowSubscriptionsHandler implements InputMessageHandler {

	private ReplyMessageService messageService;
	private UserSubscriptionService subscriptionService;
	private CryptoBot cryptoBot;

	@Autowired
	public ShowSubscriptionsHandler(ReplyMessageService messageService,
	                                UserSubscriptionService subscriptionService,
	                                @Lazy CryptoBot cryptoBot) {
		this.messageService = messageService;
		this.subscriptionService = subscriptionService;
		this.cryptoBot = cryptoBot;
	}

	@Override
	public SendMessage handle(Message message) {
		return processUsersInput(message);
	}

	private SendMessage processUsersInput(Message inputMsg){
		String chatId = inputMsg.getChatId().toString();
		String buffer = "Ваши подписки:\n";
		Optional<UserSubscription> userSubscriptionCheck = Optional.ofNullable(subscriptionService.getUserProfileData(chatId));
		if (userSubscriptionCheck.isPresent()) {
			UserSubscription userSubscription = userSubscriptionCheck.get();
			if (userSubscription.getBitcoin() == BITCOIN) buffer += "\nBitcoin " + Emojis.BELL + "\n";
			if (userSubscription.getEthereum() == ETHEREUM) buffer += "\nEthereum " + Emojis.BELL + "\n";
			if (userSubscription.getLitecoin() == LITECOIN) buffer += "\nLitecoin " + Emojis.BELL + "\n";
			if (userSubscription.getDogecoin() == DOGECOIN) buffer += "\nDogecoin " + Emojis.BELL + "\n";
			cryptoBot.sendMessage(chatId,buffer);
		}
		SendMessage replyToUser = messageService.getReplyMessage(chatId, "reply.subscriptions");

		return replyToUser;
	}

	@Override
	public BotState getHandlerName() {
		return BotState.SHOW_SUBSCRIPTIONS;
	}
}


