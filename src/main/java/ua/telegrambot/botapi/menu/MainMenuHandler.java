package ua.telegrambot.botapi.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.telegrambot.botapi.BotState;
import ua.telegrambot.botapi.InputMessageHandler;
import ua.telegrambot.service.MainMenuService;
import ua.telegrambot.service.ReplyMessageService;

@Component
public class MainMenuHandler implements InputMessageHandler {
	private ReplyMessageService messagesService;
	private MainMenuService mainMenuService;

	public MainMenuHandler(ReplyMessageService messagesService, MainMenuService mainMenuService) {
		this.messagesService = messagesService;
		this.mainMenuService = mainMenuService;
	}

	@Override
	public SendMessage handle(Message message) {
		return mainMenuService.getMainMenuMessage(message.getChatId().toString(), messagesService.getReplyText("reply.showMainMenu"));
	}

	@Override
	public BotState getHandlerName() {
		return BotState.SHOW_MAIN_MENU;
	}


}