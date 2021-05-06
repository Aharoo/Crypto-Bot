package ua.telegrambot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.telegrambot.botapi.model.UserSubscription;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription,Long> {
	UserSubscription findByChatid(String chatid);
	void deleteByChatid(String chatid);
}
