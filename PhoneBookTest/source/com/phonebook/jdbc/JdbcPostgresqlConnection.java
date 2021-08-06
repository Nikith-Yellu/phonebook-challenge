package com.phonebook.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcPostgresqlConnection {
	public static final String DRIVER_CLASS = "org.postgresql.Driver"; 

	public static Connection establishConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName(DRIVER_CLASS); 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		Move the properties to properties file and fetch from there
		try {
			String dbURL2 = "jdbc:postgresql://localhost:5432/nikith";
			String user = "nikith";
			String pass = "test1234";

				conn = DriverManager.getConnection(dbURL2, user, pass);
				if (conn != null) {
					System.out.println("Connection established");
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
