package com.helper.constants;

import java.util.HashMap;
import java.util.Map;

public class GoodWords {

	private static final Map<String, Integer> goodWords;

	public boolean isGoodWord(String word){
		return (null != goodWords.get(word));
	}

	static
	{
		goodWords = new HashMap<String, Integer>();

		goodWords.put("a",1);
		goodWords.put("love",1);
		goodWords.put("great",1);
		goodWords.put("good",1);
		goodWords.put("best",1);
		goodWords.put("like",1);
		goodWords.put("awesome",1);
		goodWords.put("works",1);
		goodWords.put("nice",1);
		goodWords.put("better",1);
		goodWords.put("thanks",1);
		goodWords.put("amazing",1);
		goodWords.put("easy",1);
		goodWords.put("well",1);
		goodWords.put("excellent",1);
		goodWords.put("cool",1);
		goodWords.put("thank",1);
		goodWords.put("fast",1);
		goodWords.put("loved",1);
		goodWords.put("perfect",1);
		goodWords.put("clean",1);
		goodWords.put("useful",1);
		goodWords.put("fun",1);
		goodWords.put("recommend",1);
	}
}

