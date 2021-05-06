package ua.telegrambot.controller;

import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.telegrambot.CryptoBot;

@RestController
public class WebHookController {
	private final CryptoBot cryptoBot;


	public WebHookController(CryptoBot cryptoBot) {
		this.cryptoBot = cryptoBot;
	}

	@PostMapping("/")
	public BotApiMethod<?> onUpdateReceived(@RequestBody Update update){
		return cryptoBot.onWebhookUpdateReceived(update);
	}
}
