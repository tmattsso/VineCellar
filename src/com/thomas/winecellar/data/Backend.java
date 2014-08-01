package com.thomas.winecellar.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thomas.winecellar.data.Wine.WineType;

public class Backend {

	public static List<Wine> getWines() {

		try {
			final Connection connection = DBTools.getConnection();

			final PreparedStatement prepareStatement = connection
					.prepareStatement("SELECT * FROM wines");
			final ResultSet executeQuery = prepareStatement.executeQuery();

			return populate(executeQuery);

		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static List<Wine> populate(ResultSet result) throws SQLException {

		final List<Wine> wines = new ArrayList<Wine>();

		while (result.next()) {
			final Wine w = new Wine();
			w.setId(result.getInt("id"));
			w.setComment(result.getString("comment"));
			w.setName(result.getString("name"));
			w.setProducer(result.getString("producer"));
			w.setType(WineType.values()[result.getInt("type")]);
			wines.add(w);
		}

		return wines;
	}

	public static Wine save(Wine w) throws SQLException {

		if (w.getId() != -1) {
			// already inserted, update
			final Connection connection = DBTools.getConnection();

			final PreparedStatement prepareStatement = connection
					.prepareStatement("UPDATE wines SET "
							+ "name=?, comment=?, producer=?, type=?, amount=?, country=?, year=?"
							+ " WHERE id=?");

			prepareStatement.setString(1, w.getName());
			prepareStatement.setString(2, w.getComment());
			prepareStatement.setString(3, w.getProducer());
			prepareStatement.setInt(4, w.getType().ordinal());
			prepareStatement.setInt(5, w.getAmount());
			prepareStatement.setString(6, w.getCountry());
			prepareStatement.setInt(7, w.getYear());

			prepareStatement.setInt(8, w.getId());

			prepareStatement.executeUpdate();

		} else {
			// insert
			final Connection connection = DBTools.getConnection();

			final PreparedStatement insert = connection
					.prepareStatement(
							"INSERT INTO wines VALUES(?,?,?,?,?,?,?,DEFAULT,?,?,?,?,?)",
							java.sql.Statement.RETURN_GENERATED_KEYS);

			int col = 1;

			insert.setString(col++, w.getName());
			insert.setString(col++, w.getComment());
			insert.setString(col++, w.getProducer());
			insert.setInt(col++, w.getType().ordinal());
			insert.setInt(col++, w.getAmount());
			insert.setString(col++, w.getCountry());
			insert.setInt(col++, w.getYear());

			insert.setString(col++, null);
			insert.setString(col++, null);
			insert.setString(col++, null);
			insert.setString(col++, null);
			insert.setString(col++, null);

			final int result = insert.executeUpdate();

			if (result != 1) {
				throw new SQLException("no primary key generated");
			}

			final ResultSet generatedKeys = insert.getGeneratedKeys();
			generatedKeys.next();
			w.setId(generatedKeys.getInt(8));
		}

		return w;
	}

	public static List<String> getCountryList() {
		return getStringList("country");
	}

	private static List<String> getStringList(String col) {
		try {
			final Connection connection = DBTools.getConnection();

			final PreparedStatement prepareStatement = connection
					.prepareStatement("SELECT DISTINCT " + col
							+ " FROM wines ORDER BY " + col + " ASC");
			final ResultSet executeQuery = prepareStatement.executeQuery();

			final List<String> countries = new ArrayList<String>();
			while (executeQuery.next()) {
				countries.add(executeQuery.getString(col));
			}

			return countries;

		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getProducerList() {
		return getStringList("producer");
	}
}
