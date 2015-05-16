package com.entities;

public class Application {

	String appRating;
	String appPrice;
	String appIcon;
	String appRateFive;
	String appRateFour;
	String appRateThree;
	String appRateTwo;
	String appRateOne;
	String appReviewCnt;
	
	String appName;
	public Application(String appName, String appRating, String appPrice,
			String appIcon, String appRateFive, String appRateFour,
			String appRateThree, String appRateTwo, String appRateOne,
			String appReviewCnt) {
		super();
		this.appName = appName;
		this.appRating = appRating;
		this.appPrice = appPrice;
		this.appIcon = appIcon;
		this.appRateFive = appRateFive;
		this.appRateFour = appRateFour;
		this.appRateThree = appRateThree;
		this.appRateTwo = appRateTwo;
		this.appRateOne = appRateOne;
		this.appReviewCnt = appReviewCnt;
	}
	
	public String getAppRating() {
		return appRating;
	}
	public void setAppRating(String appRating) {
		this.appRating = appRating;
	}
	public String getAppPrice() {
		return appPrice;
	}
	public void setAppPrice(String appPrice) {
		this.appPrice = appPrice;
	}
	public String getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}
	public String getAppRateFive() {
		return appRateFive;
	}
	public void setAppRateFive(String appRateFive) {
		this.appRateFive = appRateFive;
	}
	public String getAppRateFour() {
		return appRateFour;
	}
	public void setAppRateFour(String appRateFour) {
		this.appRateFour = appRateFour;
	}
	public String getAppRateThree() {
		return appRateThree;
	}
	public void setAppRateThree(String appRateThree) {
		this.appRateThree = appRateThree;
	}
	public String getAppRateTwo() {
		return appRateTwo;
	}
	public void setAppRateTwo(String appRateTwo) {
		this.appRateTwo = appRateTwo;
	}
	public String getAppRateOne() {
		return appRateOne;
	}
	public void setAppRateOne(String appRateOne) {
		this.appRateOne = appRateOne;
	}
	public String getAppReviewCnt() {
		return appReviewCnt;
	}
	public void setAppReviewCnt(String appReviewCnt) {
		this.appReviewCnt = appReviewCnt;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
