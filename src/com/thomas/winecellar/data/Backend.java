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
}
