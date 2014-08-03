package com.thomas.winecellar.util;

import java.io.File;
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

		final String fileContent = ParseUtil
				.readFile("/Users/Thomas/wines.csv");

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
				final String type = ParseUtil.get(row, i++);
				String name = ParseUtil.get(row, i++);
				final String year = ParseUtil.get(row, i++);
				final String area = ParseUtil.get(row, i++);
				final String country = ParseUtil.get(row, i++);
				final String producer = ParseUtil.get(row, i++);
				final String num = ParseUtil.get(row, i++);
				final String from = ParseUtil.get(row, i++);
				final String senast = ParseUtil.get(row, i++);
				final String best = ParseUtil.get(row, i++);
				final String WA = ParseUtil.get(row, i++);
				final String WS = ParseUtil.get(row, i++);
				final String LG = ParseUtil.get(row, i++);
				final String other = ParseUtil.get(row, i++);
				final String druvor = ParseUtil.get(row, i++);
				final String comment = ParseUtil.get(row, i++);

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
				insert.setInt(col++, ParseUtil.parseType(type));
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

}
