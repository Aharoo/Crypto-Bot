package ua.telegrambot.cache;

import org.springframework.stereotype.Component;
import ua.telegrambot.botapi.BotState;
import ua.telegrambot.botapi.model.UserSubscription;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {
	Map<Integer,BotState> usersBotStates = new HashMap<>();
	Map<Integer, UserSubscription> usersProfileData = new HashMap<>();

	@Override
	public void setUsersCurrentBotState(int userId, BotState botState) {
		usersBotStates.put(userId,botState);
	}

	@Override
	public BotState getUsersCurrentBotState(int userId) {
		BotState botState = usersBotStates.get(userId);
		if (botState == null)
			botState = BotState.SHOW_MAIN_MENU;
		return botState;
	}

	@Override
	public UserSubscription getUserProfileData(int userId) {
		UserSubscription userSubscription = usersProfileData.get(userId);
		if (userSubscription == null)
			userSubscription = new UserSubscription();
		return userSubscription;
	}

	@Override
	public void saveUserProfileData(int userId, UserSubscription userSubscription) {
		usersProfileData.put(userId, userSubscription);
	}
}
