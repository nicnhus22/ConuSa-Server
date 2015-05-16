package com.entities;

public class WordOccurence {
	
	private String  word;
	private Integer occurence;

	public WordOccurence(String word, Integer occurence) {
		this.word = word;
		this.occurence = occurence;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Integer getOccurence() {
		return occurence;
	}
	public void setOccurence(Integer occurence) {
		this.occurence = occurence;
	}
}
