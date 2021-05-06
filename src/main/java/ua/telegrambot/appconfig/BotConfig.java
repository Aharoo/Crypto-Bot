package ua.telegrambot.appconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ua.telegrambot.CryptoBot;
import ua.telegrambot.botapi.TelegramFacade;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
	private String webHookPath;
	private String botUserName;
	private String botToken;

	@Bean
	public CryptoBot myTelegramBot(TelegramFacade telegramFacade){
		CryptoBot cryptoBot = new CryptoBot(telegramFacade);
		cryptoBot.setBotToken(botToken);
		cryptoBot.setWebHookPath(webHookPath);
		cryptoBot.setBotUserName(botUserName);

		return cryptoBot;
	}

	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

}
