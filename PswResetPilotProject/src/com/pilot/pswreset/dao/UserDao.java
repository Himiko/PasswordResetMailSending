package com.pilot.pswreset.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pilot.pswreset.dbmgr.DatabaseConnection;
import com.pilot.pswrest.action.LoginAction;
import com.pilot.pswrest.action.RegistrationAction;
import com.sun.crypto.provider.RSACipher;

public class UserDao {
	/** register user detail contents*/
	public static int register(RegistrationAction reg){
		int status=0;
		try{
			DatabaseConnection dbConnection = new DatabaseConnection();
			Connection connection = dbConnection.getMyDBConnection();
			try{
					PreparedStatement ps=connection.prepareStatement("insert into HashPassword values(?,?,?,?,?)");
					ps.setInt(1, reg.getUserId());
					ps.setString(2,reg.getUserName());
					ps.setString(3, reg.getPassword());
					ps.setString(4, reg.getEmailAddress());
					ps.setString(5, reg.getSaltValue());
					status=ps.executeUpdate();
				}catch(SQLException ex){
					System.out.println("SQL statement is not executed!" + ex);
				}
				connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		return status;
	}
	
	public static int rowCount(String inputUserName) {
		int numberOfRows = 0;
		try {
			DatabaseConnection dbConnection = new DatabaseConnection();
			Connection connection = dbConnection.getMyDBConnection();
			try {
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM HashPassword WHERE userName=?");
				ps.setString(1, inputUserName);
				ResultSet rs = ps.executeQuery();
				rs.last();
				numberOfRows = rs.getRow();
			} catch (SQLException ex) {
				System.out.println("SQL statement is not executed!" + ex);
			}
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numberOfRows;
	}
	
	
	/** check login user is already registered in DB */
	public static LoginAction userRecord(String inputUserName) {
		LoginAction loginUser = new LoginAction();
		try {
			DatabaseConnection dbConnection = new DatabaseConnection();
			Connection connection = dbConnection.getMyDBConnection();
			try {
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM HashPassword WHERE userName=?");
				ps.setString(1, inputUserName);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					loginUser.setPassword(rs.getString("password"));
					loginUser.setSaltValue(rs.getString("saltValue"));
				}
				rs.last();	
				rs.beforeFirst();
			} catch (SQLException ex) {
				System.out.println("SQL statement is not executed!" + ex);
			}
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginUser;
	}
}