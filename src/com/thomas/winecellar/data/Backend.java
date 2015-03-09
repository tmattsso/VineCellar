package com.thomas.winecellar.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.thomas.winecellar.data.Wine.WineType;

public class Backend {

	private static Logger log = LogManager.getLogger("Backend");

	final private static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private static final int HASH_ITERATIONS = 1000;

	public static List<Wine> getWines(User u) throws BackendException {

		log.debug("loading wines..");
		try {
			final Connection connection = DBTools.getConnection();

			final PreparedStatement prepareStatement = connection
					.prepareStatement("SELECT * FROM wines w WHERE w.user_id=? ORDER BY name ASC");
			prepareStatement.setInt(1, u.getId());
			final ResultSet executeQuery = prepareStatement.executeQuery();

			final List<Wine> populate = populate(executeQuery);

			log.debug("loaded " + populate.size() + " wines.");

			return populate;

		} catch (final SQLException e) {
			log.error(e);
		}

		throw new BackendException("Could not load wines");
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

	public static Wine save(Wine w, User u) throws BackendException {

		try {
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
								"INSERT INTO wines VALUES(?,?,?,?,?,?,?,DEFAULT,?,?,?,?,?,?)",
								java.sql.Statement.RETURN_GENERATED_KEYS);

				int col = 1;

				insert.setString(col++, w.getName());
				insert.setString(col++, w.getComment());
				insert.setString(col++, w.getProducer());
				insert.setInt(col++, w.getType().ordinal());
				insert.setInt(col++, w.getAmount());
				insert.setString(col++, w.getCountry());
				insert.setInt(col++, w.getYear());

				insert.setString(col++, w.getRegion());
				insert.setString(col++, w.getDrinkBest());
				insert.setString(col++, w.getDrinkUntil());
				insert.setString(col++, w.getDrinkBest());
				insert.setString(col++, w.getGrapes());

				insert.setInt(col++, u.getId());

				final int result = insert.executeUpdate();

				if (result != 1) {
					throw new SQLException("no primary key generated");
				}

				final ResultSet generatedKeys = insert.getGeneratedKeys();
				generatedKeys.next();
				w.setId(generatedKeys.getInt(8));
			}
		} catch (final SQLException e) {
			log.error(e);

			throw new BackendException("Could not store wine");
		}

		return w;
	}

	public static List<String> getCountryList() throws BackendException {
		return getStringList("country");
	}

	private static List<String> getStringList(String col)
			throws BackendException {
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
			log.error(e);
		}

		throw new BackendException("Could not run query");
	}

	public static List<String> getProducerList() throws BackendException {
		return getStringList("producer");
	}

	public static List<String> getRegionList() throws BackendException {
		return getStringList("area");
	}

	public static List<Wine> getWines(SearchTerms terms, User u)
			throws BackendException {

		try {
			final Connection connection = DBTools.getConnection();

			String sql = "SELECT * FROM wines ";

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

			sql += " w.user_id=?";

			// trim last 'and' and sort
			sql += " ORDER BY name ASC";

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
			prepareStatement.setInt(param++, u.getId());

			final ResultSet executeQuery = prepareStatement.executeQuery();
			return populate(executeQuery);

		} catch (final SQLException e) {
			log.error(e);
		}
		throw new BackendException("Could not fetch wines");
	}

	public static User login(String email, String pass) throws BackendException {
		log.debug("loading wines..");
		try {
			final Connection connection = DBTools.getConnection();

			final PreparedStatement prepareStatement = connection
					.prepareStatement("SELECT * FROM appusers WHERE email=?");
			prepareStatement.setString(1, email);
			final ResultSet executeQuery = prepareStatement.executeQuery();

			if (!executeQuery.next()) {
				throw new BackendException("Incorrect email or password");
			}

			final User u = new User();
			u.setEmail(executeQuery.getString("email"));
			u.setHashedPass(executeQuery.getString("hashedpass"));
			u.setId(executeQuery.getInt("id"));

			final String hash = hash(getSalt(u), pass);

			if (u.getHashedPass().equals(getSalt(u) + hash)) {
				return u;
			}

		} catch (final SQLException e) {
			log.error(e);
		}
		throw new BackendException("Incorrect email or password");
	}

	public static User register(String email, String password)
			throws BackendException {

		checkValid(email, password);
		final String salt = UUID.randomUUID().toString()
				.substring(0, User.SALT_LENGTH_CHARS);

		final String both = salt + hash(salt, password);

		try {
			final String s = "INSERT INTO appusers VALUES(DEFAULT, ?, ?)";
			final PreparedStatement prepareStatement = DBTools.getConnection()
					.prepareStatement(s,
							java.sql.Statement.RETURN_GENERATED_KEYS);

			prepareStatement.setString(1, email);
			prepareStatement.setString(2, both);

			prepareStatement.executeUpdate();

			return login(email, password);

		} catch (final SQLException e) {
			log.error(e);
			throw new BackendException("Could not register user");
		}
	}

	private static void checkValid(String email, String password)
			throws BackendException {
		if (email == null || email.length() < 6) {
			throw new BackendException("Email is too short");
		}
		if (password == null || password.length() < 4) {
			throw new BackendException("Pin is too short (min. 4 numbers)");
		}
	}

	private static String getSalt(User u) {
		return u.getHashedPass().substring(0, User.SALT_LENGTH_CHARS);
	}

	private static String hash(String salt, String pwd) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.reset();
			final byte[] bytes = salt.getBytes();
			for (int i = 0; i < HASH_ITERATIONS; i++) {
				md.update(bytes);
				md.update(pwd.getBytes());
			}
			return bytesToHex(md.digest());
		} catch (final NoSuchAlgorithmException e) {
			log.error(e);
		}
		return null;
	}

	private static String bytesToHex(byte[] bytes) {
		final char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			final int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
