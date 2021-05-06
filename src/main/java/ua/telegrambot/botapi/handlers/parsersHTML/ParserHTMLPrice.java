package ua.telegrambot.botapi.handlers.parsersHTML;

import org.jsoup.nodes.Document;
import ua.telegrambot.botapi.Currencies;

public interface ParserHTMLPrice {

	Currencies getHandlerName();

	String getPrice();

	Document getWebPage();

	String getPriceFromString(String stringPrice);

}
