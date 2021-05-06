package ua.telegrambot.botapi.handlers;

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
public class FillingProfileHandler implements InputMessageHandler {

	private ReplyMessageService messagesService;

	public FillingProfileHandler(ReplyMessageService messagesService) {
		this.messagesService = messagesService;
	}

	@Override
	public SendMessage handle(Message message) {
		return processUsersInput(message);
	}
	@Override
	public BotState getHandlerName() {
		return BotState.FILLING_PROFILE;
	}

	private SendMessage processUsersInput(Message inputMsg){
		String chatId = inputMsg.getChatId().toString();

		SendMessage replyToUser = messagesService.getReplyMessage(chatId,"reply.subscribeMessage");
		replyToUser.setReplyMarkup(getCryptoCurrencyButtons());

		return replyToUser;
	}

	private InlineKeyboardMarkup getCryptoCurrencyButtons(){
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
		InlineKeyboardButton buttonBitcoin = new InlineKeyboardButton("Bitcoin");
		InlineKeyboardButton buttonLitecoin = new InlineKeyboardButton("Litecoin");
		InlineKeyboardButton buttonDogecoin = new InlineKeyboardButton("Dogecoin");
		InlineKeyboardButton buttonEthereum = new InlineKeyboardButton("Ethereum");

		buttonBitcoin.setCallbackData("BitcoinSubscribe");
		buttonLitecoin.setCallbackData("LitecoinSubscribe");
		buttonDogecoin.setCallbackData("DogecoinSubscribe");
		buttonEthereum.setCallbackData("EthereumSubscribe");

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
