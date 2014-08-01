package com.thomas.winecellar.ui.iphone;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.ui.WinePresenter;
import com.thomas.winecellar.ui.WineView;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.ui.Notification;

public class IphoneView extends NavigationManager implements WineView {

	private static final long serialVersionUID = 8222285875396851695L;

	private WinePresenter presenter;
	{
		presenter = new WinePresenter();
		setSizeFull();
	}

	@Override
	public void attach() {
		super.attach();
		presenter.init(this);
	}

	@Override
	public void load(List<Wine> wines) {
		final BrowseWinePanel comp = new BrowseWinePanel(wines, presenter);
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

}
