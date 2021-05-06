package ua.telegrambot.cache;

import ua.telegrambot.botapi.BotState;
import ua.telegrambot.botapi.model.UserSubscription;

public interface DataCache {

	void setUsersCurrentBotState(int userId, BotState botState);

	BotState getUsersCurrentBotState(int userId);

	UserSubscription getUserProfileData(int userId);

	void saveUserProfileData(int userId, UserSubscription userSubscription);

}
