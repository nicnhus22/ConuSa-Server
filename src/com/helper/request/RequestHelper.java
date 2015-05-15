package com.helper.request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.entities.application.Application;
import com.helper.db.DatabaseHelper;
import com.helper.json.JSONHelper;

public class RequestHelper {


	// HTTP POST request
	public static JSONObject sendReviewsPost() throws Exception {
		
		URL url = new URL("https://play.google.com/store/getreviews");
		String output = "";
		for(int i=41;i<42;i++){
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write("reviewType=0&pageNum="+i+"&id=com.shazam.android&reviewSortOrder=4");
			writer.flush();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				output += line;
			}
			writer.close();
			reader.close();
		}
		output = output.replace("\\u003c", "<").replace("\\u003e", ">").replace("\\u003d", "=").replace("\\", "").substring(16);
		
		return JSONHelper.buildApplicationJSON(output);
			
	}

	public static void sendApplicationPost() throws Exception {
		final String playRoot = "https://play.google.com/store/apps/details?id=";
		
		ArrayList<String> apps = new ArrayList<String>();
//		apps.add("com.instagram.android");
//		apps.add("com.ijinshan.kbatterydoctor_en");
//		apps.add("com.adobe.reader");
//		apps.add("com.bbm");
//		apps.add("com.android.chrome");
//		apps.add("com.cleanmaster.mguard");
//		apps.add("com.dropbox.android");
//		apps.add("com.facebook.katana");
//		apps.add("org.mozilla.firefox");
//		apps.add("com.shazam.android");
//		apps.add("com.skype.raider");
//		apps.add("com.snapchat.android");
//		apps.add("com.spotify.music");
//		apps.add("com.viber.voip");
		apps.add("com.soundcloud.android");
		
		for(String app: apps){
			String url = playRoot.concat(app);
			Document doc = Jsoup.connect(url).get();
			
			String appName 		= doc.select("div[itemprop=name]").text();
			String appRating 	= doc.select("div.score").text();
			String appPrice		= doc.select("button.price.buy").select("meta[itemprop=price]").attr("content");
			String appIcon 		= doc.select("img.cover-image").attr("src");
			String appRateFive  = doc.select("div.five > span.bar-number").text();
			String appRateFour 	= doc.select("div.four > span.bar-number").text();
			String appRateThree = doc.select("div.three > span.bar-number").text();
			String appRateTwo 	= doc.select("div.two > span.bar-number").text();
			String appRateOne 	= doc.select("div.one > span.bar-number").text();
			String appReviewCnt = doc.select("div.reviews-stats > span.reviews-num").text();
			
			DatabaseHelper.insertApplicationInfo(new Application(appName,appRating,appPrice,appIcon,appRateFive,appRateFour,appRateThree,appRateTwo,appRateOne,appReviewCnt));
			
		}
		
	}
}


