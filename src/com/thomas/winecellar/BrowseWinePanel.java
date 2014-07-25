package com.thomas.winecellar;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class BrowseWinePanel extends NavigationView {

	private static final long serialVersionUID = 3298846700229147335L;
	private final List<Wine> wines;
	private final VerticalLayout main;

	public BrowseWinePanel(List<Wine> wines) {

		this.wines = wines;
		main = new VerticalLayout();
		setContent(main);

	}

	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();

		for (final Wine w : wines) {
			final Button c = new Button(w.getName());
			c.addStyleName(BaseTheme.BUTTON_LINK);
			main.addComponent(c);
		}

		getNavigationBar().setCaption("Winecellar App");

		final Button search = new Button();
		search.setIcon(FontAwesome.SEARCH);
		getNavigationBar().setRightComponent(search);
	}
}
