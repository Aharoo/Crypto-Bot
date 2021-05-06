package ua.telegrambot.service;


import org.springframework.stereotype.Service;
import ua.telegrambot.botapi.model.UserSubscription;
import ua.telegrambot.repository.UserSubscriptionRepository;
import java.util.List;

@Service
public class UserSubscriptionService {

	private UserSubscriptionRepository repository;

	public UserSubscriptionService(UserSubscriptionRepository repository) {
		this.repository = repository;
	}

	public List<UserSubscription> getAllProfiles(){return repository.findAll();}

	public void saveUserSubscription(UserSubscription userSubscription){repository.save(userSubscription);}

	public void deleteUserProfileData(String profileDataId){repository.deleteByChatid(profileDataId); }

	public UserSubscription getUserProfileData(String chatid){return repository.findByChatid(chatid);}

}
