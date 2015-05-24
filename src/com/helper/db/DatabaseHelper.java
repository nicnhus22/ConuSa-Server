package com.helper.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;

import com.entities.Application;
import com.entities.WordProbability;
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
			String query = "SELECT id, reviewText FROM ApplicationReview WHERE appName=? AND reviewRating=?";
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
				JSONObject review = new JSONObject();
				String text  = rs.getString("reviewText");
				String id	 = rs.getString("id");
				
				parser.parseSentence(parser, text);
				
				review.put("text", text);
				review.put("id", id);
				
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
	 * 
	 * @param rating
	 * @param limit
	 * @return JSONObject
	 * 
	 * Gets the probabilities for a specific
	 * 
	 */
	public static List<WordProbability> fetchProbabilities(int rating, int limit) {
		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		List<WordProbability> probas = new ArrayList<WordProbability>();
		try {
			db = Database.getDatabase();
			String query = "";
			
			if(rating == 0)
				query = "SELECT * FROM ProbabilitiesAll ORDER BY probability DESC LIMIT "+limit;
			if(rating == 1)
				query = "SELECT * FROM ProbabilitiesOne ORDER BY probability DESC LIMIT "+limit;
			if(rating == 2)
				query = "SELECT * FROM ProbabilitiesTwo ORDER BY probability DESC LIMIT "+limit;
			if(rating == 3)
				query = "SELECT * FROM ProbabilitiesThree ORDER BY probability DESC LIMIT "+limit;
			if(rating == 4)
				query = "SELECT * FROM ProbabilitiesFour ORDER BY probability DESC LIMIT "+limit;
			if(rating == 5)
				query = "SELECT * FROM ProbabilitiesFive ORDER BY probability DESC LIMIT "+limit;
			
			
			stmt = db.prepareStatement(query);

			System.out.println("Database ready");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("LOG - Can't connect to database");
		}

		try {

			rs = stmt.executeQuery();		
			int i=1;
			while(rs.next()){
				JSONObject proba = new JSONObject();
				String word  		= rs.getString("word");
				float  probability  = rs.getFloat("probability");
				
				probas.add(new WordProbability(word, probability));			 
			}
			
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return probas;
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

	public static void insertWordProbabilities(List<Map<String, Integer>> wordMap){

		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 

		for(int rating=0; rating<wordMap.size();++rating){
			try {
				db = Database.getDatabase();
				String query = "";

				if(rating==0)
					query = "INSERT INTO ProbabilitiesOne VALUES(?,?)";
				if(rating==1)
					query = "INSERT INTO ProbabilitiesTwo VALUES(?,?)";
				if(rating==2)
					query = "INSERT INTO ProbabilitiesThree VALUES(?,?)";
				if(rating==3)
					query = "INSERT INTO ProbabilitiesFour VALUES(?,?)";
				if(rating==4)
					query = "INSERT INTO ProbabilitiesFive VALUES(?,?)";

				System.out.println(query);
				stmt = db.prepareStatement(query);

				System.out.println("Database ready");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				System.out.println("LOG - Can't connect to database");
			}

			try {
				Map<String, Integer> map = wordMap.get(rating);
				for (Entry<String, Integer> entry : map.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();

					// Compute Probability
					float wordMapSize 	= map.size();
					float wordOccurence 	= (int)value;
					float probability	= wordOccurence/wordMapSize;

					stmt.setString(1, key);
					stmt.setFloat(2, probability);

					try{
						stmt.executeUpdate();
					}catch(Exception e){
						System.out.println("continuing");
						e.printStackTrace();
					}
					
					System.out.println("[DEBUG] - Adding into rating "+rating+" ("+key+","+probability+")");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}

	public static void insertWordProbabilitiesAll(Map<String, Integer> wordMap){
		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 

		try {
			db = Database.getDatabase();
			String query = "INSERT INTO ProbabilitiesAll VALUES(?,?)";

			System.out.println(query);
			stmt = db.prepareStatement(query);

			System.out.println("Database ready");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("LOG - Can't connect to database");
		}

		try {
			for (Entry<String, Integer> entry : wordMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				// Compute Probability
				float wordMapSize 	= wordMap.size();
				float wordOccurence = (int)value;
				float probability	= wordOccurence/wordMapSize;

				stmt.setString(1, key);
				stmt.setFloat(2, probability);

				stmt.executeUpdate();
				System.out.println("[DEBUG] - Adding into all("+key+","+probability+")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}

	public static String addToGoldStandard(String id, String current,
			String override) {
		String result = "";
		
		// Get connection to DB
		Connection db = null;
		PreparedStatement stmt = null; 

		try {
			db = Database.getDatabase();
			String query = "INSERT INTO GoldStandard VALUES(?,?,?)";

			System.out.println(query);
			stmt = db.prepareStatement(query);

			stmt.setString(1, id);
			stmt.setInt(2, Integer.parseInt(current));
			stmt.setInt(3, Integer.parseInt(override));

			stmt.executeUpdate();
			System.out.println("[DEBUG] - Overriding review with ID="+id);
			result = "Review rating updated";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("LOG - Can't connect to database");
			result = "Internal error, please retry";
		}

		return result;
	}

}
