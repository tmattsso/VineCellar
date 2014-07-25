package com.thomas.winecellar;

import java.util.List;

import com.thomas.winecellar.data.Backend;
import com.thomas.winecellar.data.Wine;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ComputerView extends VerticalLayout {

	private static final long serialVersionUID = -961075109819808061L;

	{
		final List<Wine> wines = Backend.getWines();

		final Table t = new Table("wines", new BeanItemContainer<Wine>(
				Wine.class, wines));
		t.setSizeFull();
		addComponent(t);
		setSizeFull();
	}
}
