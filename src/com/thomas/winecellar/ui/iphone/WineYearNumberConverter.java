package com.thomas.winecellar.ui.iphone;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class WineYearNumberConverter implements Converter<String, Integer> {

	private static final long serialVersionUID = -5716706217906456877L;

	@Override
	public Integer convertToModel(String value,
			Class<? extends Integer> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
		value = value.replaceAll(",", "").replaceAll("\\.", "");
		if (value.isEmpty()) {
			return 0;
		}
		return Integer.parseInt(value);
	}

	@Override
	public String convertToPresentation(Integer value,
			Class<? extends String> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
		return value == null || value.equals(0) ? "" : value.toString();
	}

	@Override
	public Class<Integer> getModelType() {
		return Integer.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
