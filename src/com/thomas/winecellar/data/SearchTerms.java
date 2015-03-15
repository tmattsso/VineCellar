package com.thomas.winecellar.data;

import com.thomas.winecellar.data.Wine.WineType;

public class SearchTerms {

	public String text;
	public String country;
	public String region;
	public String producer;

	public int yearmin = -1;
	public int yearmax = -1;

	public WineType type;

	public boolean includeZeros;

	public boolean isChanged() {
		return text != null || country != null || region != null
				|| producer != null || type != null || yearmin != -1
				|| yearmax != -1;
	}
}
