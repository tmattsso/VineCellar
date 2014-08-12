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
					.prepareStatement("SELECT * FROM wines ORDER BY name ASC");
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
			w.setYear(result.getInt("year"));
			w.setAmount(result.getInt("amount"));
			w.setComment(result.getString("comment"));
			w.setName(result.getString("name"));
			w.setProducer(result.getString("producer"));
			w.setRegion(result.getString("area"));
			w.setCountry(result.getString("country"));
			w.setDrinkFrom(result.getString("drinkfrom"));
			w.setDrinkUntil(result.getString("drinklast"));
			w.setDrinkBest(result.getString("drinkbest"));
			w.setGrapes(result.getString("grapes"));
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
							+ "name=?, comment=?, producer=?, type=?, amount=?, "
							+ "country=?, year=?, area=?, drinkfrom=?, drinklast=?, "
							+ "drinkbest=?, grapes=?" + " WHERE id=?");

			int col = 1;
			prepareStatement.setString(col++, w.getName());
			prepareStatement.setString(col++, w.getComment());
			prepareStatement.setString(col++, w.getProducer());
			prepareStatement.setInt(col++, w.getType().ordinal());
			prepareStatement.setInt(col++, w.getAmount());
			prepareStatement.setString(col++, w.getCountry());
			prepareStatement.setInt(col++, w.getYear());
			prepareStatement.setString(col++, w.getRegion());
			prepareStatement.setString(col++, w.getDrinkFrom());
			prepareStatement.setString(col++, w.getDrinkUntil());
			prepareStatement.setString(col++, w.getDrinkBest());
			prepareStatement.setString(col++, w.getGrapes());

			prepareStatement.setInt(col++, w.getId());

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
				final String country = executeQuery.getString(col);
				if (country != null && country.length() > 1) {
					countries.add(country);
				}
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

	public static List<String> getRegionList() {
		return getStringList("area");
	}

	public static List<Wine> getWines(SearchTerms terms) {

		try {
			final Connection connection = DBTools.getConnection();

			String sql = "SELECT * FROM wines ";

			if (terms.isChanged()) {
				sql += "WHERE ";
			}
			if (terms.text != null && terms.text.length() > 0) {
				sql += "(lower(name) LIKE ? " + "OR lower(area) LIKE ? "
						+ "OR lower(country) LIKE ? "
						+ "OR lower(comment) LIKE ? "
						+ "OR lower(producer) LIKE ?) " + "AND ";
			}
			if (terms.region != null) {
				sql += "area=? AND ";
			}
			if (terms.country != null) {
				sql += "country=? AND ";
			}
			if (terms.producer != null) {
				sql += "producer=? AND ";
			}
			if (terms.type != null) {
				sql += "type=? AND ";
			}
			if (terms.yearmin != -1) {
				sql += "year>=? AND ";
			}
			if (terms.yearmax != -1) {
				sql += "year<=? AND ";
			}

			// trim last 'and' and sort
			if (terms.isChanged()) {
				sql = sql.substring(0, sql.length() - 4) + " ORDER BY name ASC";
			}

			final PreparedStatement prepareStatement = connection
					.prepareStatement(sql);

			int param = 1;
			if (terms.text != null && terms.text.length() > 0) {
				prepareStatement.setString(param++, "%" + terms.text + "%");
				prepareStatement.setString(param++, "%" + terms.text + "%");
				prepareStatement.setString(param++, "%" + terms.text + "%");
				prepareStatement.setString(param++, "%" + terms.text + "%");
				prepareStatement.setString(param++, "%" + terms.text + "%");
			}
			if (terms.region != null) {
				prepareStatement.setString(param++, terms.region);
			}
			if (terms.country != null) {
				prepareStatement.setString(param++, terms.country);
			}
			if (terms.producer != null) {
				prepareStatement.setString(param++, terms.producer);
			}
			if (terms.type != null) {
				prepareStatement.setInt(param++, terms.type.ordinal());
			}
			if (terms.yearmin != -1) {
				prepareStatement.setInt(param++, terms.yearmin);
			}
			if (terms.yearmax != -1) {
				prepareStatement.setInt(param++, terms.yearmax);
			}

			final ResultSet executeQuery = prepareStatement.executeQuery();
			return populate(executeQuery);

		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
