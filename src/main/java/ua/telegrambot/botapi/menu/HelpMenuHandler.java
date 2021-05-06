package ua.telegrambot.botapi.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.telegrambot.botapi.BotState;
import ua.telegrambot.botapi.InputMessageHandler;
import ua.telegrambot.service.MainMenuService;
import ua.telegrambot.service.ReplyMessageService;

@Component
public class HelpMenuHandler implements InputMessageHandler {
	private MainMenuService mainMenuService;
	private ReplyMessageService messagesService;

	public HelpMenuHandler(MainMenuService mainMenuService, ReplyMessageService messagesService) {
		this.mainMenuService = mainMenuService;
		this.messagesService = messagesService;
	}

	@Override
	public SendMessage handle(Message message) {
		return mainMenuService.getMainMenuMessage(message.getChatId().toString(),
				messagesService.getReplyText("reply.showHelpMenu"));
	}

	@Override
	public BotState getHandlerName() {
		return BotState.SHOW_HELP_MENU;
	}
}