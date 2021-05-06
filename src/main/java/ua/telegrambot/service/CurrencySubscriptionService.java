package ua.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ua.telegrambot.botapi.Currencies;
import ua.telegrambot.botapi.model.UserSubscription;
import ua.telegrambot.cache.UserDataCache;
import static ua.telegrambot.botapi.Currencies.*;
import static ua.telegrambot.botapi.Currencies.ETHEREUM;

@Service
public class CurrencySubscriptionService {

	private UserDataCache userDataCache;
	private UserSubscriptionService userSubscriptionService;

	@Autowired
	public CurrencySubscriptionService(UserDataCache userDataCache, UserSubscriptionService userSubscriptionService) {
		this.userDataCache = userDataCache;
		this.userSubscriptionService = userSubscriptionService;
	}

	public void subscribeUserCurrency(Currencies currency, CallbackQuery userQuery){
		String chatid = userQuery.getMessage().getChatId().toString();
		int userid = userQuery.getFrom().getId();
		UserSubscription userSubscription = userDataCache.getUserProfileData(userid);
		userSubscription.setChatid(chatid);
		if (currency.equals(BITCOIN)) userSubscription.setBitcoin(BITCOIN);
		if (currency.equals(DOGECOIN)) userSubscription.setDogecoin(DOGECOIN);
		if (currency.equals(LITECOIN)) userSubscription.setLitecoin(LITECOIN);
		if (currency.equals(ETHEREUM)) userSubscription.setEthereum(ETHEREUM);
		userDataCache.saveUserProfileData(userid,userSubscription);
		userSubscriptionService.saveUserSubscription(userSubscription);
	}

	public void unsubscribeUserCurrency(Currencies currency, CallbackQuery userQuery){
		String chatid = userQuery.getMessage().getChatId().toString();
		int userid = userQuery.getFrom().getId();
		UserSubscription userSubscription = userDataCache.getUserProfileData(userid);
		userSubscription.setChatid(chatid);
		if (currency.equals(BITCOIN)) userSubscription.setBitcoin(null);
		if (currency.equals(DOGECOIN)) userSubscription.setDogecoin(null);
		if (currency.equals(LITECOIN)) userSubscription.setLitecoin(null);
		if (currency.equals(ETHEREUM)) userSubscription.setEthereum(null);
		userDataCache.saveUserProfileData(userid,userSubscription);
		userSubscriptionService.saveUserSubscription(userSubscription);
	}
}
