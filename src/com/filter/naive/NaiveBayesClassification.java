package com.filter.naive;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.helper.db.DatabaseHelper;
import com.helper.string.Parser;

public class NaiveBayesClassification {

	
	
	public NaiveBayesClassification(){
		
	}
	
	public static void main(String[] args){
		NaiveBayesClassification classif = new NaiveBayesClassification();
		int ratingPrediction = 0;
		float maxScore = Float.NEGATIVE_INFINITY;

		for(int i=1; i<6; ++i){
			float score = classif.sentenceScore("Stupid I can't log out bad worst hate shit sucks",i);
			if (score > maxScore){
				maxScore = score;
				ratingPrediction = i;
			}
		}
		
		System.out.println(ratingPrediction);
	}
	
	public float sentenceScore(String sentence, int rating){
		float score = 0;
		Parser parser = new Parser();
		parser.parseSentence(parser, sentence);
		
		Iterator it = parser.getOccurenceMap().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        String word = (String)pair.getKey();
	        float  prob = DatabaseHelper.fetchWordProbabilityPerStar(word, rating);
	        if(prob != -1)
	        	score += Math.log(prob);
	        else
	        	score += 0.1;
	    }
		
		return score;
	}
	
}
