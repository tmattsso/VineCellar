package com.thomas.winecellar.ui.iphone;

import com.thomas.winecellar.ui.WinePresenter;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class WineToolbar extends Toolbar {

	private static final long serialVersionUID = -5424019068247983406L;

	public WineToolbar(final WinePresenter presenter) {

		final Button add = new Button("Add wine");
		add.setIcon(FontAwesome.PLUS);
		addComponent(add);

		add.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -9043716252613242039L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.addClicked();
			}
		});

		final Button search = new Button("Search");
		search.setIcon(FontAwesome.SEARCH);
		addComponent(search);

		search.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -9043716252613242039L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.searchClicked();
			}
		});

		final Button settings = new Button("Settings");
		settings.setIcon(FontAwesome.GEAR);
		addComponent(settings);

		settings.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -8983594031510326761L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.settingsClicked();
			}
		});
	}
}
