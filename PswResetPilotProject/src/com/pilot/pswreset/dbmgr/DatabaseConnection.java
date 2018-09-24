package com.pilot.pswreset.dbmgr;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	private Connection myDBConnection;

	public Connection getMyDBConnection() {
		return myDBConnection;
	}

	public void setMyDBConnection(Connection myDBConnection) {
		this.myDBConnection = myDBConnection;
	}

	public DatabaseConnection() {
		// Connect to database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/testhash";
			myDBConnection = DriverManager.getConnection(url, "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			myDBConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
