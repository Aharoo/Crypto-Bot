package ua.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.telegrambot.botapi.Currencies;
import ua.telegrambot.botapi.handlers.parsersHTML.ParserHTMLPrice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParserHTMLPriceService {
	private Map<Currencies, ParserHTMLPrice> parsers = new HashMap<>();

	@Autowired
	public ParserHTMLPriceService(List<ParserHTMLPrice> parsers) {
		parsers.forEach(parser -> this.parsers.put(parser.getHandlerName(),parser));
	}

	public String processInputMessage(Currencies currentState){
		ParserHTMLPrice currentMessageHandler = findMessageHandler(currentState);
	    return currentMessageHandler.getPrice();
	}

	private ParserHTMLPrice findMessageHandler(Currencies currentState){
		return parsers.get(currentState);
	}
}
