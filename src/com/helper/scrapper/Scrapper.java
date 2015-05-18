package com.helper.scrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.helper.db.Database;
import com.helper.db.DatabaseHelper;
import com.helper.string.Parser;

public class Scrapper {


	public static void main(String[] args) {

		//List<String> apps = Scrapper.getAllApplications();
		//List<Map<String, Integer>> wordProbabilityPerRating = Scrapper.buildProbabilityMap();
		
		//DatabaseHelper.insertWordProbabilities(wordProbabilityPerRating);
		
		Map<String, Integer> wordProbabilityAll = Scrapper.buildProbabilityMapAll();
		DatabaseHelper.insertWordProbabilitiesAll(wordProbabilityAll);
	}

	public static List<String> getAllApplications(){
		List<String> applications = new ArrayList<String>();

		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		
		try {
			db = Database.getDatabase();

			String query = "SELECT appName FROM Application";
			stmt = db.prepareStatement(query);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("LOG - Can't connect to database");
		}

		try {

			rs = stmt.executeQuery();		

			while(rs.next()){
				//Retrieve by column name
				String name  = rs.getString("appName");
				applications.add(name);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		



		return applications;
	}

	public static List<Map<String, Integer>> buildProbabilityMap(){
		List<Map<String, Integer>> wordProbabilityPerRating = new ArrayList<Map<String, Integer>>();

		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		Parser parser = new Parser();
		
		for(int rating=1; rating<6; ++rating){
			try {
				db = Database.getDatabase();
	
				String query = "SELECT reviewText FROM ApplicationReview";
				stmt = db.prepareStatement(query);
				stmt.setInt(1, rating);
	
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				System.out.println("LOG - Can't connect to database");
			}
	
			try {
	
				rs = stmt.executeQuery();		
	
				while(rs.next()){
					//Retrieve by column name
					String review  = rs.getString("reviewText");
					parser.parseSentence(parser, review);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			parser.sortByOccurences();
			wordProbabilityPerRating.add(parser.getOccurenceMap());
		}


		return wordProbabilityPerRating;
	}

	public static Map<String, Integer> buildProbabilityMapAll(){

		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		Parser parser = new Parser();
		
			try {
				db = Database.getDatabase();
	
				String query = "SELECT reviewText FROM ApplicationReview";
				stmt = db.prepareStatement(query);

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				System.out.println("LOG - Can't connect to database");
			}
	
			try {
	
				rs = stmt.executeQuery();		
				
				while(rs.next()){
					//Retrieve by column name
					String review  = rs.getString("reviewText");
					parser.parseSentence(parser, review);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			parser.sortByOccurences();


		return parser.getOccurenceMap();
	}

}
