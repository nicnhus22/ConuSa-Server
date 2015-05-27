package com.helper.analysis;

import java.util.List;

import org.json.simple.JSONObject;

import com.helper.db.DatabaseHelper;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(computeGoldStandardScore());
		
	}
	// 50  reviews: 75%
	// 100 reviews: 80%
	// 150 reviews: 82%
	public static float computeGoldStandardScore(){
		
		float result = 0;
		List<JSONObject> list = DatabaseHelper.fetchGoldStandard();
		int   listSize = list.size();
		int   good=1;
		
		for(JSONObject obj: list){
			int oldRating = (int)obj.get("oldRating");
			int newRating = (int)obj.get("newRating");
			
			if((oldRating - 1) == newRating || (oldRating + 1) == newRating || oldRating == newRating){
				good++;
			} 
		}
		
		result = ((float)good/(float)listSize); 
		
		return result;
	}

}
