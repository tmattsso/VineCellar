package com.thomas.vinecellar.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Backend {

	public static List<Wine> getWines() {

		final List<Wine> wines = new ArrayList<Wine>();
		try {
			final Connection connection = DBTools.getConnection();

			final PreparedStatement prepareStatement = connection
					.prepareStatement("SELECT * FROM wines");
			final ResultSet executeQuery = prepareStatement.executeQuery();

			while (executeQuery.next()) {
				final Wine w = new Wine();
				w.setId(executeQuery.getInt("id"));
				wines.add(w);
			}
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wines;
	}
}
