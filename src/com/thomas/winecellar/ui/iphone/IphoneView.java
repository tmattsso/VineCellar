package com.thomas.winecellar.ui.iphone;

import java.util.List;

import com.thomas.winecellar.data.User;
import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.ui.FragmentNavigator;
import com.thomas.winecellar.ui.VinecellarUI;
import com.thomas.winecellar.ui.WinePresenter;
import com.thomas.winecellar.ui.WineView;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationEvent.Direction;
import com.vaadin.ui.Notification;

public class IphoneView extends FragmentNavigator implements WineView {

	private static final long serialVersionUID = 8222285875396851695L;

	protected WinePresenter presenter;

	private BrowseWinePanel listView;

	protected Wine selectedWine;

	public IphoneView() {
		presenter = new WinePresenter();
		setSizeFull();

		addNavigationListener(new NavigationListener() {

			private static final long serialVersionUID = -4073678099862664340L;

			@Override
			public void navigate(NavigationEvent event) {

				final boolean currentIsListView = currentInListView();
				if (event.getDirection() == Direction.BACK && currentIsListView) {

					// refresh wine list
					presenter.init(IphoneView.this);
				}
			}
		});
	}

	@Override
	public void attach() {
		super.attach();
		presenter.init(this);
	}

	protected boolean currentInListView() {
		return getCurrentComponent() instanceof BrowseWinePanel;
	}

	@Override
	public void load(List<Wine> wines, boolean searchResults) {

		if (currentInListView()) {
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
	public void showDetails(Wine w) {
		selectedWine = w;
		if (getCurrentComponent() instanceof WineDetailsPanel) {
			navigateBack();
		}
		if (listView != null) {
			listView.setShortCaption();
		}
		navigateTo(new WineDetailsPanel(w, presenter, false));
	}

	@Override
	public void showError(String msg) {
		Notification.show(msg);
	}

	@Override
	public void showEdit(Wine wine) {
		selectedWine = wine;
		navigateTo(new WineDetailsPanel(wine, presenter, true));
	}

	@Override
	public void showSearch() {
		navigateTo(new SearchPanel(presenter));
	}

	@Override
	public User getUser() {
		return VinecellarUI.getUser();
	}

	@Override
	public void showSettings() {
		navigateTo(new SettingsPanel(presenter));
	}

}
