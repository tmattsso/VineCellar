package com.thomas.winecellar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.thomas.winecellar.data.DBTools;
import com.thomas.winecellar.data.Wine.WineType;

public class CSVImporter {

	public static void main(String[] args) throws Exception {

		System.out.println("Starting import...");

		final File f = new File("/Users/Thomas/wines.csv");

		if (!f.exists()) {
			System.out.println("No file found");
			return;
		}

		final Connection connection = DBTools.getConnection();

		final String fileContent = readFile("/Users/Thomas/wines.csv");

		final String[] lines = fileContent.split("\r\n");

		try {

			final PreparedStatement clearOld = connection
					.prepareStatement("DELETE FROM wines");
			clearOld.executeUpdate();

			System.out.println("Old list cleared");

			int rowNum = 1;

			for (final String line : lines) {

				// skip header
				if (rowNum == 1) {
					rowNum++;
					continue;
				}

				rowNum++;

				final String[] row = line.split(";");
				int i = 0;
				final String type = get(row, i++);
				String name = get(row, i++);
				final String year = get(row, i++);
				final String area = get(row, i++);
				final String country = get(row, i++);
				final String producer = get(row, i++);
				final String num = get(row, i++);
				final String from = get(row, i++);
				final String senast = get(row, i++);
				final String best = get(row, i++);
				final String WA = get(row, i++);
				final String WS = get(row, i++);
				final String LG = get(row, i++);
				final String other = get(row, i++);
				final String druvor = get(row, i++);
				final String comment = get(row, i++);

				if (name == null) {

					System.out.println("Skipped row " + rowNum);
					continue;
				}

				if (name.length() == 0) {
					name = "(not given)";
				}

				final PreparedStatement insert = connection
						.prepareStatement(
								"INSERT INTO wines VALUES(?,?,?,?,?,?,?,DEFAULT,?,?,?,?,?)",
								java.sql.Statement.RETURN_GENERATED_KEYS);
				int col = 1;

				insert.setString(col++, name);
				insert.setString(col++, comment);
				insert.setString(col++, producer);
				insert.setInt(col++, parseType(type));
				insert.setInt(col++, ParseUtil.getInt(num));
				insert.setString(col++, country);
				insert.setInt(col++, ParseUtil.getInt(year));

				insert.setString(col++, area);
				insert.setString(col++, from);
				insert.setString(col++, senast);
				insert.setString(col++, best);
				insert.setString(col++, druvor);

				insert.executeUpdate();

				System.out.println("Inserted '" + name + "'");
			}

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	private static String readFile(String filename) {
		String content = null;
		final File file = new File(filename); // for ex foo.txt
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), "ISO-8859-1"));
			final char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (final Exception IGNORE) {
			}
		}
		return content;
	}

	private static String get(String[] row, int i) {
		if (row.length <= i) {
			return null;
		}
		return row[i];
	}

	private static int parseType(String type) {
		if (type.toLowerCase().contains("rött")) {
			return WineType.RED.ordinal();
		}
		if (type.toLowerCase().contains("champ")) {
			return WineType.CHAMPAGNE.ordinal();
		}
		if (type.toLowerCase().contains("mouss")) {
			return WineType.SPARKLING.ordinal();
		}
		if (type.toLowerCase().contains("vitt")) {
			return WineType.WHITE.ordinal();
		}
		if (type.toLowerCase().contains("ros")) {
			return WineType.ROSE.ordinal();
		}
		if (type.toLowerCase().contains("sött")) {
			return WineType.SWEET.ordinal();
		}
		throw new RuntimeException("No such wine type: " + type);
	}
}
