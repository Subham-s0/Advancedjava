package com.Advancedjava.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
		private static final String URL = "jdbc:mysql://localhost:3306/" + "Nestaway";
		private static final String USERNAME = "root";
		private static final String PASSWORD = "12345678";
		static {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			}
			catch(ClassNotFoundException e){
				e.printStackTrace();
			
			}
		}
		public static Connection getDbConnection() throws SQLException {
			Connection Conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			 return Conn;
			 
			}
}
