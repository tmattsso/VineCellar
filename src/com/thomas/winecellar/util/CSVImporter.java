package com.thomas.winecellar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.thomas.winecellar.data.DBTools;

public class CSVImporter {

	public static void main(String[] args) throws Exception {

		System.out.println("Starting import...");

		final File f = new File("/Users/Thomas/wines.csv");

		if (!f.exists()) {
			System.out.println("No file found");
			return;
		}

		final Connection connection = DBTools.getConnection();

		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(f), "ISO-8859-1"));

		String line = null;

		// skip header
		reader.readLine();

		try {

			final PreparedStatement clearOld = connection
					.prepareStatement("DELETE FROM wines");
			clearOld.executeUpdate();

			System.out.println("Old list cleared");

			int rowNum = 1;

			while ((line = reader.readLine()) != null) {

				rowNum++;

				final String[] row = line.split(";");
				int i = 0;
				final String type = get(row, i++);
				final String name = get(row, i++);
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

				final PreparedStatement insert = connection
						.prepareStatement(
								"INSERT INTO wines VALUES(?,?,?,?,?,?,?,DEFAULT,?,?,?,?,?)",
								java.sql.Statement.RETURN_GENERATED_KEYS);
				int col = 1;

				insert.setString(col++, name);
				insert.setString(col++, comment);
				insert.setString(col++, producer);
				insert.setInt(col++, parseType(type));
				insert.setInt(col++, getInt(num));
				insert.setString(col++, country);
				insert.setInt(col++, getInt(year));

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
			reader.close();
			connection.close();
		}
	}

	private static int getInt(String num) {
		if (num == null) {
			return 0;
		}
		num = num.replace("-", "");
		if (num.equals("")) {
			return 0;
		}
		return Integer.parseInt(num);
	}

	private static String get(String[] row, int i) {
		if (row.length <= i) {
			return null;
		}
		return row[i];
	}

	private static int parseType(String type) {
		return 0;
	}
}
