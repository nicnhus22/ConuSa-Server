package com.helper.string;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.helper.constants.StopWords;

public class Parser {

	private Map<String, Integer> occurenceMap;
	
	public Parser(){
		occurenceMap = new HashMap<String, Integer>();
	}
	
	public static String prepareString(String input){
		return input.replace(".", " ")
					.replace("?", " ")
					.replace("!", " ")
					.replace(",", " ")
					.replace("(", " ")
					.replace(")", " ")
					.replace("-", " ")
					.replace("/", " ")
					.replace("\\", " ")
					.toLowerCase();
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
		occurenceMap = Sorter.sortByComparator(occurenceMap, true);
	}
	
	public static void main(String[] args) {
		 
		Parser parser = new Parser();
		
		String str = "This is String.        , split by StringTokenizer, string created by created by by by mkyong)((((";
		
		str = prepareString(str);
		
		StringTokenizer st = new StringTokenizer(str);
 
		System.out.println("---- Split by space ------");
		while (st.hasMoreElements()) {
			String word = (String)st.nextElement();
			if(!(word.equals("Full") || word.equals("Review")))
				parser.addToOccurenceMap(word);
		}
		
		parser.sortByOccurences();
		parser.printMap();
 
	}
	
}
