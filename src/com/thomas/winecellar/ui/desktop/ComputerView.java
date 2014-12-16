package com.thomas.winecellar.ui.desktop;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.ui.iphone.AbstractWineView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class ComputerView extends AbstractWineView {

	private static final long serialVersionUID = -961075109819808061L;
	private WineTablePanel wineTablePanel;

	@Override
	public void load(List<Wine> wines, boolean searchResults) {
		if (currentlyInListView()) {
			wineTablePanel.updateTable(wines, searchResults);
		} else {
			wineTablePanel = new WineTablePanel(presenter, wines, searchResults);
			navigateTo(wineTablePanel);
		}

		if (selectedWine != null) {
			wineTablePanel.scrollTo(selectedWine);
		}
	}

	@Override
	protected boolean currentlyInListView() {
		return getCurrentComponent() instanceof WineTablePanel;
	}

	@Override
	public void showError(String string) {
		Notification.show(string, Type.ERROR_MESSAGE);
	}

	@Override
	public void showListing(Wine wineToUpdate) {
		if (getCurrentComponent() != wineTablePanel) {
			navigateBack();
		}

		if (wineToUpdate != null) {
			wineTablePanel.updateTable(wineToUpdate);
		}
	}

}
