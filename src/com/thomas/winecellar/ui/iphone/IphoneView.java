package com.thomas.winecellar.ui.iphone;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.ui.WinePresenter;
import com.thomas.winecellar.ui.WineView;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class IphoneView extends NavigationManager implements WineView {

	private static final long serialVersionUID = 8222285875396851695L;

	private boolean maskURIChange = false;

	protected WinePresenter presenter;
	{
		presenter = new WinePresenter();
		setSizeFull();

		addNavigationListener(new NavigationListener() {

			private static final long serialVersionUID = -4073678099862664340L;

			@Override
			public void navigate(NavigationEvent event) {
				maskURIChange = true;
				final int newId = getCurrentComponent().hashCode();
				UI.getCurrent().getPage().setUriFragment(newId + "");
				maskURIChange = false;
			}
		});

		Page.getCurrent().addUriFragmentChangedListener(
				new UriFragmentChangedListener() {

					private static final long serialVersionUID = -1713918099181690469L;

					@Override
					public void uriFragmentChanged(UriFragmentChangedEvent event) {

						if (maskURIChange) {
							return;
						}

						maskURIChange = true;
						// user pressed back or forward; determine which
						try {
							final int id = Integer.parseInt(event
									.getUriFragment());

							if (getNextComponent() != null
									&& getNextComponent().hashCode() == id) {
								navigateTo(getNextComponent());
							} else if (getPreviousComponent() != null
									&& getPreviousComponent().hashCode() == id) {
								navigateBack();
							}
						} catch (final Exception e) {
							// ignore
							e.printStackTrace();
						}
						maskURIChange = false;
					}
				});
	}

	@Override
	public void attach() {
		super.attach();
		presenter.init(this);
	}

	@Override
	public void load(List<Wine> wines, boolean searchResults) {
		final BrowseWinePanel comp = new BrowseWinePanel(wines, searchResults,
				presenter);
		navigateTo(comp);
	}

	@Override
	public void showDetails(Wine w) {
		if (getCurrentComponent() instanceof WineDetailsPanel) {
			navigateBack();
		}
		navigateTo(new WineDetailsPanel(w, presenter, false));
	}

	@Override
	public void showError(Exception e) {
		Notification.show(e.getMessage());
	}

	@Override
	public void showEdit(Wine wine) {
		navigateTo(new WineDetailsPanel(wine, presenter, true));
	}

	@Override
	public void showSearch() {
		navigateTo(new SearchPanel(presenter));
	}

}
