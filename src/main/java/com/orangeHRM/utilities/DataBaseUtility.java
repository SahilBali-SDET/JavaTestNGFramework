package com.orangeHRM.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseUtility {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    static String QUERY = "";

    // Method to verify if record is present in database
    public static boolean verifyRecordArePresentInDataBase(String tableName, String columnName, String columnValue) {
    	QUERY = "SELECT * FROM " + tableName + " WHERE " + columnName + " = '" + columnValue + "'";
		try {
	        Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(QUERY); 
	        return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    // Method to verify if column value is correct in database
    public static boolean verifyColumnValueInDataBase(String tableName, String identifier, String identifierValue, String columnName, String columnValue) {
    	QUERY = "SELECT * FROM " + tableName;
		try {
	    	Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(QUERY);    
	        while (rs.next()) {
	        	String actualIdenfitierValue = rs.getString(identifier);
				if (actualIdenfitierValue.equals(identifierValue)) {
		        	String actualValue = rs.getString(columnName);
					if (actualValue.equals(columnValue)) {
						System.out.println("PASS: Record is present in database");
						return true;
					}
				}
			}
	        return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
}
