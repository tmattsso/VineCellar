package com.thomas.winecellar.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.postgresql.Driver;

public class DBTools {

	private static final int PORTNUMBER = 5432;
	private static final String SERVERNAME = "localhost";
	private static final String DBNAME = "winecellar";
	private static final String USERNAME = "vine";
	private static final String PASSWORD = "vine";

	public static Connection getConnection() throws SQLException {

		DriverManager.registerDriver(new Driver());

		Connection conn = null;
		final Properties connectionProps = new Properties();
		connectionProps.put("user", USERNAME);
		connectionProps.put("password", PASSWORD);

		conn = DriverManager.getConnection("jdbc:postgresql://" + SERVERNAME
				+ ":" + PORTNUMBER + "/" + DBNAME, connectionProps);

		return conn;
	}
}
