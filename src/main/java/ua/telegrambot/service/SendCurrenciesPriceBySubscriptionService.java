package ua.telegrambot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.telegrambot.CryptoBot;
import ua.telegrambot.botapi.model.UserSubscription;
import java.util.List;
import static ua.telegrambot.botapi.Currencies.*;
import static ua.telegrambot.botapi.Currencies.DOGECOIN;

@Service
public class SendCurrenciesPriceBySubscriptionService {

	private UserSubscriptionService subscriptionService;
	private ParserHTMLPriceService priceParserService;
	private CryptoBot cryptoBot;


	@Autowired
	public SendCurrenciesPriceBySubscriptionService(UserSubscriptionService subscriptionService,
	                                                ParserHTMLPriceService priceParserService,
	                                                @Lazy CryptoBot cryptoBot) {
		this.subscriptionService = subscriptionService;
		this.priceParserService = priceParserService;
		this.cryptoBot = cryptoBot;
	}

	@Scheduled(fixedRateString = "60000")
	public void sendPrice(){
		String buffer = "";
		List<UserSubscription> userSubscriptionList = subscriptionService.getAllProfiles();
		for (int i = 0; i < userSubscriptionList.size();i++){
			UserSubscription userSubscription = userSubscriptionList.get(0);
			if (userSubscription.getBitcoin() == BITCOIN) buffer += priceParserService.processInputMessage(BITCOIN) + "\n";
			if (userSubscription.getEthereum() == ETHEREUM) buffer += "\n" + priceParserService.processInputMessage(ETHEREUM) + "\n";
			if (userSubscription.getLitecoin() == LITECOIN) buffer += "\n" + priceParserService.processInputMessage(LITECOIN) + "\n";
			if (userSubscription.getDogecoin() == DOGECOIN) buffer += "\n" + priceParserService.processInputMessage(DOGECOIN) + "\n";
			cryptoBot.sendMessage(userSubscription.getChatid(),buffer);
		}
	}
}
