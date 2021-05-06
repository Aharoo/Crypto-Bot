package ua.telegrambot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuService {

	public SendMessage getMainMenuMessage(final String chat_id,final String textMessage) {
		final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
		final SendMessage mainMenuMessage = createMessageWithKeyboard(chat_id, textMessage, replyKeyboardMarkup);

		return mainMenuMessage;
	}

	private ReplyKeyboardMarkup getMainMenuKeyboard(){

		final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(false);

		KeyboardRow keyboardRow1 = new KeyboardRow();
		KeyboardRow keyboardRow2 = new KeyboardRow();
		KeyboardRow keyboardRow3 = new KeyboardRow();
		keyboardRow1.add(new KeyboardButton("Просмотреть цены криптовалют"));
		keyboardRow1.add(new KeyboardButton("Список подписок"));
		keyboardRow2.add(new KeyboardButton("Подписаться на криптовалюту"));
		keyboardRow2.add(new KeyboardButton("Помощь"));
		keyboardRow3.add(new KeyboardButton("Что такое криптовалюта?"));

		List<KeyboardRow> keyboard = new ArrayList<>();
		keyboard.add(keyboardRow1);
		keyboard.add(keyboardRow2);
		keyboard.add(keyboardRow3);
		replyKeyboardMarkup.setKeyboard(keyboard);
		return replyKeyboardMarkup;

	}

	private SendMessage createMessageWithKeyboard(final String chat_id,String textMessage,final ReplyKeyboardMarkup replyKeyboardMarkup){
		final SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chat_id);
		sendMessage.setText(textMessage);
		if (replyKeyboardMarkup != null)
			sendMessage.setReplyMarkup(replyKeyboardMarkup);
		return sendMessage;
	}
}
