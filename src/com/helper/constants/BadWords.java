package com.helper.constants;

import java.util.HashMap;
import java.util.Map;

public class BadWords {
	
	private static final Map<String, Integer> badWords;

	public boolean isBadWord(String word){
		return (null != badWords.get(word));
	}

	static
	{
		badWords = new HashMap<String, Integer>();

		badWords.put("fix",1);
		badWords.put("please",1);
		badWords.put("don't",1);
		badWords.put("doesn't",1);
		badWords.put("won't",1);
		badWords.put("crashes",1);
		badWords.put("working",1);
		badWords.put("problem",1);
		badWords.put("sucks",1);
		badWords.put("hate",1);
		badWords.put("bad",1);
		badWords.put("cant",1);
		badWords.put("can't",1);
		badWords.put("crashing",1);
		badWords.put("slow",1);
		badWords.put("worse",1);
		badWords.put("nothing",1);
		badWords.put("useless",1);
		badWords.put("crash",1);
		badWords.put("uninstall",1);
		badWords.put("keeps",1);
		badWords.put("creepest",1);
		badWords.put("bullshit",1);
		badWords.put("privacy",1);
		badWords.put("worthless",1);
		badWords.put("stopped",1);
		badWords.put("stupid",1);
		badWords.put("freezing",1);
		badWords.put("pointless",1);
		badWords.put("buffering",1);
		badWords.put("unfortunately",1);
		badWords.put("cannot",1);
		badWords.put("fixing",1);
		badWords.put("nothing",1);
		badWords.put("bug",1);
		badWords.put("uninstaling",1);
		badWords.put("repeatedly",1);
		badWords.put("disappointed",1);
		badWords.put("sucked",1);
		badWords.put("issue",1);
		badWords.put("crap",1);
		badWords.put("stucks",1);
		badWords.put("stuck",1);
		badWords.put("poor",1);

	}
}
