package ua.telegrambot.botapi.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.telegrambot.CryptoBot;
import ua.telegrambot.botapi.BotState;
import ua.telegrambot.botapi.InputMessageHandler;
import ua.telegrambot.service.ReplyMessageService;
import ua.telegrambot.utils.Emojis;

@Component
public class ShowInformationHandler implements InputMessageHandler {

	private ReplyMessageService messageService;
	private CryptoBot cryptoBot;

	@Autowired
	public ShowInformationHandler(ReplyMessageService messageService,
	                              @Lazy CryptoBot cryptoBot) {
		this.messageService = messageService;
		this.cryptoBot = cryptoBot;
	}

	@Override
	public SendMessage handle(Message message) {
		String chatid = message.getChatId().toString();
		cryptoBot.sendMessage(chatid, messageService.getReplyText("reply.cryptoinformationlink"));

		return messageService.getReplyMessage(chatid,"reply.cryptoinformation", Emojis.SCROLL);
	}

	@Override
	public BotState getHandlerName() {
		return BotState.SHOW_CRYPTO_INFORMATION;
	}
}
