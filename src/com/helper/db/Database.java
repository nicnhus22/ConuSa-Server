package com.helper.db;

import java.sql.*;
import java.util.Properties;


public class Database {

	// The JDBC Connector Class.
	private static final String dbClassName = "com.mysql.jdbc.Driver";

	// Connection string. emotherearth is the database the program
	// is connecting to. You can include user and password after this
	// by adding (say) ?user=paulr&password=paulr. Not recommended!

	private static final String CONNECTION =
			"jdbc:mysql://localhost/ConuSa";

	public static Connection getDatabase() throws ClassNotFoundException,SQLException{
		// Class.forName(xxx) loads the jdbc classes and
		// creates a drivermanager class factory
		Class.forName(dbClassName);

		// Properties for user and password. Here the user and password are both 'paulr'
		Properties p = new Properties();
		p.put("user","root");
		p.put("password","root");

		// Now try to connect
		Connection c = DriverManager.getConnection(CONNECTION,p);
		return c;
	}
}
