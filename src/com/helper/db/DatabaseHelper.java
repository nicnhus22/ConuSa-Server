package com.helper.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import com.entities.Application;
import com.helper.string.Parser;

public class DatabaseHelper {

	/**
	 * 
	 * @param appName
	 * @param rating
	 * @return JSONObject
	 * 
	 * Gets all the reviews for a specific application and rating.
	 * 
	 */
	public static JSONObject fetchReviews(String appName, String rating){ 
		
		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		JSONObject reviewsOccurence = new JSONObject();
		JSONObject reviews			= new JSONObject();
		Parser parser = new Parser();
		try {
			db = Database.getDatabase();
			String query = "SELECT reviewText FROM ApplicationReview WHERE appName=? AND reviewRating=?";
			stmt = db.prepareStatement(query);
			
			System.out.println("Database ready");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("LOG - Can't connect to database");
		}
		
		int i=-1;
		try {
			stmt.setString(1, appName);
			stmt.setInt(2, Integer.parseInt(rating));
			
			rs = stmt.executeQuery();
			i=1;
			while(rs.next()){
			     //Retrieve by column name
				 String review  = rs.getString("reviewText");
				 
				 parser.parseSentence(parser, review);
				 
				 reviews.put("review"+i, review);
				 i++;
			}
			parser.sortByOccurences();
			parser.printMap();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		// Add occurence map for reviews
		reviewsOccurence.put("occurenceMap", parser.mapToObjectArray(parser.getOccurenceMap()));
		reviewsOccurence.put("reviews", reviews);
		reviewsOccurence.put("reviewCount", i);
		
		return reviewsOccurence;
		
	}
	
	/**
	 * 
	 * @param appName
	 * @param fetchAll
	 * @return JSONObject
	 * 
	 * Gets all the application if fetchAll is True. 
	 * Gets a single application info if fetchAll is False.
	 * 
	 */
	public static JSONObject fetchApplications(String appName, Boolean fetchAll){
		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		JSONObject apps = new JSONObject();
		try {
			db = Database.getDatabase();
			
			if(!fetchAll.booleanValue()){
				String query = "SELECT * FROM Application WHERE appName=?";
				stmt = db.prepareStatement(query);
				stmt.setString(1, appName);
				System.out.println("not all");
			} else {
				String query = "SELECT * FROM Application";
				stmt = db.prepareStatement(query);
			}
			
			System.out.println("Database ready");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("LOG - Can't connect to database");
		}
		
		try {
			
			rs = stmt.executeQuery();		
			
			while(rs.next()){
			     //Retrieve by column name
				 String name  		= rs.getString("appName");
				 Float rating  		= rs.getFloat("appRating");
				 Float price  		= rs.getFloat("appPrice");
				 String icon  		= rs.getString("appIcon");
				 Integer five  		= rs.getInt("appRateFive");
				 Integer four  		= rs.getInt("appRateFour");
				 Integer three  	= rs.getInt("appRateThree");
				 Integer two  		= rs.getInt("appRateTwo");
				 Integer one  		= rs.getInt("appRateOne");
				 Integer reviewCtn  = rs.getInt("appReviewCnt");
				 
				 JSONObject app = new JSONObject();
				 app.put("name", name);
				 app.put("rating", rating);
				 app.put("price", price);
				 app.put("icon", icon);
				 app.put("five", five);
				 app.put("four", four);
				 app.put("three", three);
				 app.put("two", two);
				 app.put("one", one);
				 app.put("reviewCtn", reviewCtn);
				 
				 apps.put(name, app);				 
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		return apps;
	}
	
	/**
	 * @param app
	 * 
	 * Insert all the applications' information
	 */
	public static void insertApplicationInfo(Application app){
		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 
		try {
			db = Database.getDatabase();
			String query = "INSERT INTO Application VALUES(?,?,?,?,?,?,?,?,?,?)";
			stmt = db.prepareStatement(query);
			
			System.out.println("Database ready");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("LOG - Can't connect to database");
		}
		
		try {
			stmt.setString(1, app.getAppName());
			stmt.setFloat(2, Float.parseFloat(app.getAppRating().replace(",", "")));
			stmt.setFloat(3, Float.parseFloat(app.getAppPrice().replace(",", "")));
			stmt.setString(4, app.getAppIcon());
			stmt.setInt(5, Integer.parseInt(app.getAppRateFive().replace(",", "")));
			stmt.setInt(6, Integer.parseInt(app.getAppRateFour().replace(",", "")));
			stmt.setInt(7, Integer.parseInt(app.getAppRateThree().replace(",", "")));
			stmt.setInt(8, Integer.parseInt(app.getAppRateTwo().replace(",", "")));
			stmt.setInt(9, Integer.parseInt(app.getAppRateOne().replace(",", "")));
			stmt.setInt(10, Integer.parseInt(app.getAppReviewCnt().replace(",", "")));
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	
}
