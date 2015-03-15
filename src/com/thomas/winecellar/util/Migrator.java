package com.thomas.winecellar.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.postgresql.Driver;

public class Migrator {

	private static final int PORTNUMBER = 5432;
	private static final String SERVERNAME = "localhost";
	private static final String DBNAME = "winecellar2";
	private static final String USERNAME = "vine";
	private static final String PASSWORD = "vine";

	public static void run() throws Exception {

		DriverManager.registerDriver(new Driver());

		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", USERNAME);
		connectionProps.put("password", PASSWORD);

		conn = DriverManager.getConnection("jdbc:postgresql://" + SERVERNAME
				+ ":" + PORTNUMBER + "/winecellar", connectionProps);

		final PreparedStatement s = conn
				.prepareStatement("SELECT * FROM wines");
		final ResultSet r = s.executeQuery();

		conn = null;
		connectionProps = new Properties();
		connectionProps.put("user", USERNAME);
		connectionProps.put("password", PASSWORD);

		conn = DriverManager.getConnection("jdbc:postgresql://" + SERVERNAME
				+ ":" + PORTNUMBER + "/" + DBNAME, connectionProps);

		if (!r.next()) {
			throw new RuntimeException("no wines");
		}

		final int userId = 5;

		while (!r.isAfterLast()) {

			final PreparedStatement insert = conn
					.prepareStatement(
							"INSERT INTO wines VALUES(?,?,?,?,?,?,?,DEFAULT,?,?,?,?,?,?)",
							java.sql.Statement.RETURN_GENERATED_KEYS);

			int col = 1;

			insert.setString(col++, r.getString("name"));
			insert.setString(col++, r.getString("comment"));
			insert.setString(col++, r.getString("producer"));
			insert.setInt(col++, r.getInt("type"));
			insert.setInt(col++, r.getInt("amount"));
			insert.setString(col++, r.getString("country"));
			insert.setInt(col++, r.getInt("year"));

			insert.setString(col++, r.getString("area"));
			insert.setString(col++, r.getString("drinkfrom"));
			insert.setString(col++, r.getString("drinklast"));
			insert.setString(col++, r.getString("drinkbest"));
			insert.setString(col++, r.getString("grapes"));

			insert.setInt(col++, userId);

			final int result = insert.executeUpdate();

			if (result != 1) {
				throw new SQLException("no primary key generated");
			}
			r.next();
		}
	}
}
