package com.thomas.winecellar;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.vaadin.addon.touchkit.ui.NavigationManager;

public class IphoneView extends NavigationManager implements WineView {

	private static final long serialVersionUID = 8222285875396851695L;

	private WinePresenter presenter;
	{
		presenter = new WinePresenter();
		presenter.init(this);
	}

	@Override
	public void load(List<Wine> wines) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDetails(Wine w) {
		// TODO Auto-generated method stub

	}

}
