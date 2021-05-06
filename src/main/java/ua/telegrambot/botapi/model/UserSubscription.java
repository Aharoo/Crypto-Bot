package ua.telegrambot.botapi.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ua.telegrambot.botapi.Currencies;
import javax.persistence.*;
import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table
public class UserSubscription implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String chatid;

	@Enumerated(EnumType.STRING)
	Currencies bitcoin;

	@Enumerated(EnumType.STRING)
	Currencies dogecoin;

	@Enumerated(EnumType.STRING)
	Currencies ethereum;

	@Enumerated(EnumType.STRING)
	Currencies litecoin;

}
