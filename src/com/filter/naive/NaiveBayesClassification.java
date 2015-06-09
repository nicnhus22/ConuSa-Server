package com.filter.naive;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.helper.constants.BadWords;
import com.helper.constants.GoodWords;
import com.helper.db.DatabaseHelper;
import com.helper.string.Parser;

public class NaiveBayesClassification {

	private static GoodWords goodWords;
	private static BadWords  badWords;
	
	private static double ALMOST_NEVER = 0.00000000001;
	
	public NaiveBayesClassification(){
		
	}
	
	// 50 reviews: 90%
	public static void main(String[] args){
		
		NaiveBayesClassification classif = new NaiveBayesClassification();
		
		List<String> goldStrings1 = DatabaseHelper.fetchGoldStandard(1);
		List<String> goldStrings5 = DatabaseHelper.fetchGoldStandard(5);
		
		int goodClass = 0;
		for(String review: goldStrings1){
			int prediction = classif.getHighestScore(review);
			if((prediction) == 1 ){//|| (prediction) == 2
				goodClass++;
			} else {
				System.out.println(prediction+" - "+review);
			}
		}
		for(String review: goldStrings5){
			int prediction = classif.getHighestScore(review);
			if((prediction) == 5 ){ //|| (prediction) == 5
				goodClass++;
			} else {
				System.out.println(prediction+" - "+review);
			}
		}
		System.out.println((float)goodClass/((float)goldStrings1.size()+(float)goldStrings5.size()));
	}
	
	public float sentenceScore(String sentence, int rating){
		goodWords = new GoodWords();
		badWords  = new BadWords();
		
		float score = 0;
		Parser parser = new Parser();
		parser.parseSentence(parser, sentence);
		
		Iterator it = parser.getOccurenceMap().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        String word = ((String)pair.getKey()).toLowerCase();
	        
	        if(badWords.isBadWord(word) && rating < 3)
	        	score += Math.log(0.99);
	        else if(badWords.isBadWord(word) && rating > 3)
	        	score += Math.log(ALMOST_NEVER);
	        else if(goodWords.isGoodWord(word) && rating < 3)
	        	score += Math.log(ALMOST_NEVER);
	        else if(goodWords.isGoodWord(word) && rating > 3)
	        	score += Math.log(0.99);
	        else{
	        	float  prob = DatabaseHelper.fetchWordProbabilityPerStar(word, rating);
		        if(prob != -1)
		        	score += Math.log(prob);
		        else
		        	score += Math.log(ALMOST_NEVER);
	        }
	    }
	    
	    float ratingProb = ((float)DatabaseHelper.fetchCountPerRating(rating)/(float)DatabaseHelper.fetchCountPerRating(0)); 
	    score += Math.log(ratingProb);
		
		return score;
	}
	
	public int getHighestScore(String sentence){
		NaiveBayesClassification classif = new NaiveBayesClassification();
		int ratingPrediction = 0;
		float maxScore = Float.NEGATIVE_INFINITY;
		
		// Classify 1 stars
		for(int i=1; i<6; ++i){
			if(i != 3){
				float score = classif.sentenceScore(sentence,i);
				if (score > maxScore){
					maxScore = score;
					ratingPrediction = i;
				}
			}			
		}
		
		return ratingPrediction;
		
	}
	
}
