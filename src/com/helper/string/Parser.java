package com.helper.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.entities.WordOccurence;
import com.helper.constants.StopWords;

public class Parser {
	
	private static int minOccurence = 10;

	private Map<String, Integer> occurenceMap;
	
	public Parser(){
		occurenceMap = new HashMap<String, Integer>();
	}
	
	public static String prepareString(String input){
		return input.replaceAll("[^A-Za-z']+", " ").replace("Full", "").replace("Review", "").toLowerCase();
	}
	
	public void addToOccurenceMap(String word){
		if(null == occurenceMap.get(word)){
			occurenceMap.put(word, 1);
		} else if (occurenceMap.get(word).intValue() > 0){
			int val = occurenceMap.get(word).intValue();
			val++;
			occurenceMap.put(word, val);
		}
	}
	
	public void printMap(){
		System.out.println(occurenceMap.toString());
	}
	
	public void parseSentence(Parser parser,String sentence){
		StopWords stopWords = new StopWords();
		
		sentence = prepareString(sentence);
		
		StringTokenizer st = new StringTokenizer(sentence);
		
		while (st.hasMoreElements()) {
			String word = (String)st.nextElement();
			if(!stopWords.isStopWord(word))
				parser.addToOccurenceMap(word);
		}
	}
	
	public void sortByOccurences(){
		occurenceMap = Sorter.sortByComparator(occurenceMap, false);
	}
	
	public Map<String, Integer> getOccurenceMap(){
		return occurenceMap;
	}

	public List<WordOccurence> mapToObjectArray(Map<String, Integer> occurenceMap){
		
		List<WordOccurence> wordOccurence = new ArrayList<WordOccurence>();
		
		Iterator it = occurenceMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        if((Integer)pair.getValue() > minOccurence)
	        	wordOccurence.add(new WordOccurence((String)pair.getKey(), (Integer)pair.getValue()));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		return wordOccurence;
	}
}
