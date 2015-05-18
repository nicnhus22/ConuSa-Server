package com.entities;

public class WordProbability {

	private String word;
	private float probability;
	
	public WordProbability(String word, float probability) {
		super();
		this.word = word;
		this.probability = probability;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}
	
	
}
