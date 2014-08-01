package com.thomas.winecellar.ui;

import java.util.List;

import com.thomas.winecellar.data.Wine;

public interface WineView {

	public void load(List<Wine> wines);

	public void showDetails(Wine w);

	public void showError(Exception e);

	public void showEdit(Wine wine);
}
