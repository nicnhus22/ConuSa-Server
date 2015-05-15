package com.helper.json;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.helper.db.Database;

public class JSONHelper {

	public static JSONObject buildApplicationJSON(String postOut) {
		
		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 
		try {
			db = Database.getDatabase();
			String query = "INSERT INTO ApplicationReview VALUES(?,?,?,?)";
			stmt = db.prepareStatement(query);
			
			System.out.println("Database ready");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("LOG - Can't connect to database");
		}
		
		JSONObject apps = new JSONObject();
		
		Document html = Jsoup.parse(postOut); 
		Elements reviews = html.getElementsByClass("single-review");
		int reviewCtr = 1;
		for(Element review: reviews){
			// Get review ID for DB
			String reviewID = review.getElementsByClass("review-header")
										.attr("data-reviewid");
			
			// Get star review
			String stars = review.getElementsByClass("star-rating-non-editable-container")
									.attr("aria-label")
									.replaceAll("[^0-9]", "");
			// Get review body
			String text  = review.getElementsByClass("review-body").text();
			
			JSONObject appObj = new JSONObject();
			apps.put("App"+reviewCtr, appObj);
			appObj.put("reviewID", reviewID);
			appObj.put("starRating",stars);
			appObj.put("reviewBody",text);
			
			// Add tuple to database
			try {
				stmt.setString(1, reviewID);
				stmt.setString(2, "Shazam");
				stmt.setString(3, text);
				stmt.setInt(4, Integer.parseInt(stars));
				stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			reviewCtr++;
		}
		
		return apps;
	}
}
