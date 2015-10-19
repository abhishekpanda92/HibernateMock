package com.virtusa.databaseservices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConnection {
	private static Properties prop = null;
	static {
		try {
			FileInputStream fIn = new FileInputStream(
					".\\src\\com\\virtusa\\databaseservices\\oracleproperties.properties");
			prop = new Properties();
			prop.load(fIn);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static Connection getConnection() {

		Connection conn = null;
		try {
			Class.forName(prop.getProperty("driverclass"));
			conn = DriverManager.getConnection(prop.getProperty("dburl"),
					prop.getProperty("username"), prop.getProperty("password"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
