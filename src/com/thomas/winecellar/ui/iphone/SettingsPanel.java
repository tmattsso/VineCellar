package com.thomas.winecellar.ui.iphone;

import com.thomas.winecellar.ui.WinePresenter;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.VerticalLayout;

public class SettingsPanel extends NavigationView {

	public SettingsPanel(final WinePresenter presenter) {

		final VerticalLayout root = new VerticalLayout();
		root.setMargin(true);
		setContent(root);

		setCaption("Settings");

		final NavigationButton b = new NavigationButton("Change PIN",
				new ChangePinPanel(presenter));
		root.addComponent(b);
	}
}