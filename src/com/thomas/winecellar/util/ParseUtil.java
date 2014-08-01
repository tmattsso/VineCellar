package com.thomas.winecellar.util;

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
}
