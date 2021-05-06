package ua.telegrambot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {
	SPARKLES(EmojiParser.parseToUnicode(":sparkles:")),
	SCROLL(EmojiParser.parseToUnicode(":scroll:")),
	CHART_UP(EmojiParser.parseToUnicode(":chart_with_upwards_trend:")),
	CHART_DOWN(EmojiParser.parseToUnicode(":chart_with_downwards_trend:")),
	TADA(EmojiParser.parseToUnicode(":tada:")),
	BELL(EmojiParser.parseToUnicode(":bell:"));


	private String emojiName;

	@Override
	public String toString(){
		return emojiName;
	}
}
