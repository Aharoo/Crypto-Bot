package ua.telegrambot.botapi.handlers.parsersHTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import ua.telegrambot.botapi.Currencies;
import ua.telegrambot.utils.Emojis;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParserHTMLPriceEthereum implements ParserHTMLPrice {

	private Document document;
	private Pattern pattern = Pattern.compile("\\d\\,\\d{3}\\.\\d{2}");

	@Override
	public String getPriceFromString(String stringPrice){
		Matcher matcher = pattern.matcher(stringPrice);
		if (matcher.find()){
			return matcher.group();
		}
		return "Can't find price";
	}

	@Override
	public Document getWebPage(){
		try {
			document = Jsoup.connect("https://www.coindesk.com/price/ethereum").get();
		} catch (IOException e){
			e.getStackTrace();
		}
		return document;
	}


	@Override
	public Currencies getHandlerName() {
		return Currencies.ETHEREUM;
	}

	@Override
	public String getPrice(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'и время' hh:mm:ss");
		Document page = getWebPage();

		String priceString = page.select("div[class=price-large]").text();

		String price = "Ethereum: Дата " + dateFormat.format(new Date()) + ".Цена " + Emojis.CHART_UP + "$" +getPriceFromString(priceString)
				+ Emojis.CHART_DOWN;

		return price;
	}
}
