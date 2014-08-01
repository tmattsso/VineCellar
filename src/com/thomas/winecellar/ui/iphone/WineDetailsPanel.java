package com.thomas.winecellar.ui.iphone;

import com.thomas.winecellar.data.Wine;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Label;

public class WineDetailsPanel extends NavigationView {

	private static final long serialVersionUID = 8748765625250900366L;
	private final Wine w;

	public WineDetailsPanel(Wine w) {
		this.w = w;
		setContent(new Label(w.getName()));
	}

	@Override
	public void attach() {
		super.attach();

		getNavigationBar().setCaption(w.getName());

	}

}
