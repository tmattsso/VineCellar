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

			prepareStatement.setString(0, w.getName());
			prepareStatement.setString(1, w.getComment());
			prepareStatement.setString(2, w.getProducer());
			prepareStatement.setInt(3, w.getType().ordinal());
			prepareStatement.setInt(4, w.getAmount());
			prepareStatement.setString(5, w.getCountry());
			prepareStatement.setInt(6, w.getYear());

			prepareStatement.setInt(7, w.getId());

			prepareStatement.executeUpdate();

		} else {
			// insert
			final Connection connection = DBTools.getConnection();

			final PreparedStatement prepareStatement = connection
					.prepareStatement("INSERT INTO wines VALUES(null,?,?,?,?,?,?,?)");

			prepareStatement.setString(0, w.getName());
			prepareStatement.setString(1, w.getComment());
			prepareStatement.setString(2, w.getProducer());
			prepareStatement.setInt(3, w.getType().ordinal());
			prepareStatement.setInt(4, w.getAmount());
			prepareStatement.setString(5, w.getCountry());
			prepareStatement.setInt(6, w.getYear());

			prepareStatement.executeUpdate();
			final ResultSet generatedKeys = prepareStatement.getGeneratedKeys();

			if (!generatedKeys.next()) {
				throw new SQLException("no primary key generated");
			}
			w.setId(generatedKeys.getInt(0));
		}

		return w;
	}
}
