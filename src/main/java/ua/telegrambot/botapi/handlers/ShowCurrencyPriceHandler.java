package ua.telegrambot.botapi.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.telegrambot.botapi.BotState;
import ua.telegrambot.botapi.InputMessageHandler;
import ua.telegrambot.service.ReplyMessageService;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShowCurrencyPriceHandler implements InputMessageHandler {

	private ReplyMessageService messageService;

	@Autowired
	public ShowCurrencyPriceHandler(ReplyMessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public SendMessage handle(Message message) {
		return processUsersInput(message);

	}

	private SendMessage processUsersInput(Message inputMsg){
		String chatId = inputMsg.getChatId().toString();

		SendMessage replyToUser = messageService.getReplyMessage(chatId,"reply.askcurrency");
		replyToUser.setReplyMarkup(getCryptoCurrencyButtons());

		return replyToUser;
	}

	@Override
	public BotState getHandlerName() {
		return BotState.SHOW_CRYPTO_SEARCH;
	}

	private InlineKeyboardMarkup getCryptoCurrencyButtons(){
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
		InlineKeyboardButton buttonBitcoin = new InlineKeyboardButton("Bitcoin");
		InlineKeyboardButton buttonLitecoin = new InlineKeyboardButton("Litecoin");
		InlineKeyboardButton buttonDogecoin = new InlineKeyboardButton("Dogecoin");
		InlineKeyboardButton buttonEthereum = new InlineKeyboardButton("Ethereum");

		buttonBitcoin.setCallbackData("BitcoinPrice");
		buttonLitecoin.setCallbackData("LitecoinPrice");
		buttonDogecoin.setCallbackData("DogecoinPrice");
		buttonEthereum.setCallbackData("EthereumPrice");

		List<InlineKeyboardButton> keyboardButtonRow1 = new ArrayList<>();
		keyboardButtonRow1.add(buttonBitcoin);
		keyboardButtonRow1.add(buttonLitecoin);

		List<InlineKeyboardButton> keyBoardButtonRow2 = new ArrayList<>();
		keyBoardButtonRow2.add(buttonDogecoin);
		keyBoardButtonRow2.add(buttonEthereum);

		List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
		rowList.add(keyboardButtonRow1);
		rowList.add(keyBoardButtonRow2);

		inlineKeyboardMarkup.setKeyboard(rowList);

		return inlineKeyboardMarkup;
	}
}
