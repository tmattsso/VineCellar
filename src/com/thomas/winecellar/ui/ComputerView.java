package com.thomas.winecellar.ui;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.ui.desktop.WineTablePanel;
import com.thomas.winecellar.ui.iphone.IphoneView;

public class ComputerView extends IphoneView {

	private static final long serialVersionUID = -961075109819808061L;

	@Override
	public void load(List<Wine> wines, boolean searchResults) {
		navigateTo(new WineTablePanel(presenter, wines, searchResults));
	}
}
