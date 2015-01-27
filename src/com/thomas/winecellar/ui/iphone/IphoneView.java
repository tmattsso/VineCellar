package com.thomas.winecellar.ui.iphone;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.vaadin.ui.Notification;

public class IphoneView extends AbstractWineView {

	private static final long serialVersionUID = 8222285875396851695L;

	private BrowseWinePanel listView;

	public IphoneView() {

	}

	@Override
	protected boolean currentlyInListView() {
		return getCurrentComponent() instanceof BrowseWinePanel;
	}

	@Override
	public void load(List<Wine> wines, boolean searchResults) {

		if (currentlyInListView()) {
			listView.updateTable(wines, searchResults);
		} else {
			listView = new BrowseWinePanel(wines, searchResults, presenter);
			navigateTo(listView);
		}

		if (selectedWine != null) {
			listView.scrollTo(selectedWine);
		}
	}

	@Override
	public void showError(String msg) {
		Notification.show(msg);
	}

	@Override
	public void showListing(Wine wineToUpdate) {
		// navigate back to list view
		navigateToListing();

		if (wineToUpdate != null) {
			listView.updateWineInTable(wineToUpdate);
		}
	}

}
