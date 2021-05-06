package ua.telegrambot;

import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.telegrambot.botapi.TelegramFacade;
import java.io.FileNotFoundException;

public class CryptoBot extends TelegramWebhookBot {

	private String webHookPath;
	private String botUserName;
	private String botToken;

	private TelegramFacade telegramFacade;

	public CryptoBot(TelegramFacade telegramFacade){
		this.telegramFacade = telegramFacade;
	}


	public String getWebHookPath() {
		return webHookPath;
	}

	public void setWebHookPath(String webHookPath) {
		this.webHookPath = webHookPath;
	}

	public String getBotUserName() {
		return botUserName;
	}

	public void setBotUserName(String botUserName) {
		this.botUserName = botUserName;
	}

	public void setBotToken(String botToken) {
		this.botToken = botToken;
	}

	@Override
	public String getBotUsername() {
		return botUserName;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}

	@Override
	public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
		final BotApiMethod<?> replyMessageToUser = telegramFacade.handleUpdate(update);
		return replyMessageToUser;
	}

	@Override
	public String getBotPath() {
		return webHookPath;
	}

	public void sendPhoto(String chat_id,String imageCaption,String imagePath){
		File image = null;
		try {
			image = ResourceUtils.getFile("classpath:" + imagePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		SendPhoto sendPhoto = new SendPhoto().setPhoto(image);
		sendPhoto.setChatId(chat_id);
		sendPhoto.setCaption(imageCaption);
		try {
			execute(sendPhoto);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	public void sendMessage(String chatId, String textMessage) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(textMessage);

		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}



}
