package com.thomas.winecellar;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ComputerView extends VerticalLayout implements WineView {

	private static final long serialVersionUID = -961075109819808061L;

	private WinePresenter presenter;
	{
		presenter = new WinePresenter();
		presenter.init(this);
	}

	@Override
	public void load(List<Wine> wines) {
		final Table t = new Table("wines", new BeanItemContainer<Wine>(
				Wine.class, wines));
		t.setSizeFull();
		addComponent(t);
		setSizeFull();
	}

	@Override
	public void showDetails(Wine w) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showError(Exception e) {
		// TODO Auto-generated method stub

	}
}
