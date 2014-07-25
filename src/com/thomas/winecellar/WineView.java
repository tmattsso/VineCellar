package com.thomas.winecellar;

import java.util.List;

import com.thomas.winecellar.data.Wine;

public interface WineView {

	public void load(List<Wine> wines);

	public void showDetails(Wine w);

	public void showError(Exception e);
}
