package com.thomas.winecellar.ui;

import java.util.List;

import com.thomas.winecellar.data.Wine;

public interface WineView {

	public void load(List<Wine> wines, boolean searchResults);

	public void showDetails(Wine w);

	public void showError(String string);

	public void showEdit(Wine wine);

	public void showSearch();
}
