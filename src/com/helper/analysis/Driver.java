package com.helper.analysis;

import java.util.List;

import org.json.simple.JSONObject;

import com.helper.db.DatabaseHelper;
import com.helper.string.Parser;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(computeGoldStandardScore());
		
	}

	public static float computeGoldStandardScore(){
		
		float result = 0;
		List<String> list = DatabaseHelper.fetchGoldStandard(1);
		
		Parser parser = new Parser();
		
		for(String review: list){
			parser.parseSentence(parser, review);			
		}
		parser.sortByOccurences();
		System.out.println(parser.getOccurenceMap());
		
		return result;
	}

}
