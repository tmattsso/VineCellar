package com.thomas.winecellar.ui.iphone;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.ui.FragmentNavigator;
import com.thomas.winecellar.ui.WinePresenter;
import com.thomas.winecellar.ui.WineView;

public abstract class AbstractWineView extends FragmentNavigator implements
		WineView {

	private static final long serialVersionUID = 5262135515287424577L;

	protected WinePresenter presenter;
	protected Wine selectedWine;

	public AbstractWineView() {
		presenter = new WinePresenter();
		setSizeFull();
		presenter.init(this);
	}

	protected void navigateToListing() {
		while (!currentlyInListView() && getPreviousComponent() != null) {
			navigateBack();
		}
	}

	protected abstract boolean currentlyInListView();

	@Override
	public void showDetails(Wine w) {
		selectedWine = w;
		navigateToListing();
		navigateTo(new WineDetailsPanel(w, presenter));
	}

	@Override
	public void showSearch() {
		navigateTo(new SearchPanel(presenter));
	}

}