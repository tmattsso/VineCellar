package com.thomas.winecellar.ui.desktop;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.ui.iphone.IphoneView;

public class ComputerView extends IphoneView {

	private static final long serialVersionUID = -961075109819808061L;
	private WineTablePanel wineTablePanel;

	@Override
	public void load(List<Wine> wines, boolean searchResults) {
		if (currentInListView()) {
			wineTablePanel.updateTable(wines, searchResults);
		} else {
			wineTablePanel = new WineTablePanel(presenter, wines, searchResults);
			navigateTo(wineTablePanel);
		}

		wineTablePanel.scrollTo(selectedWine);
	}

	@Override
	protected boolean currentInListView() {
		return getCurrentComponent() instanceof WineTablePanel;
	}
}
