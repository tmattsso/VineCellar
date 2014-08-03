package com.thomas.winecellar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.thomas.winecellar.data.Wine.WineType;

public class ParseUtil {

	public static int getInt(String num) {
		if (num == null) {
			return 0;
		}
		num = num.replace("-", "");
		if (num.equals("")) {
			return 0;
		}
		return Integer.parseInt(num);
	}

	static String readFile(String filename) {
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

	static String get(String[] row, int i) {
		if (row.length <= i) {
			return null;
		}
		return row[i];
	}

	static int parseType(String type) {
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
