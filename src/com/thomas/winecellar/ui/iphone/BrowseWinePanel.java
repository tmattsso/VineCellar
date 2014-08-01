package com.thomas.winecellar.ui.iphone;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.ui.WinePresenter;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class BrowseWinePanel extends NavigationView {

	private static final long serialVersionUID = 3298846700229147335L;
	private final List<Wine> wines;
	private final VerticalLayout main;
	private final WinePresenter presenter;

	public BrowseWinePanel(List<Wine> wines, final WinePresenter presenter) {

		this.wines = wines;
		this.presenter = presenter;
		main = new VerticalLayout();
		setContent(main);

	}

	@Override
	public void attach() {
		super.attach();

		main.removeAllComponents();

		final VerticalComponentGroup group = new VerticalComponentGroup();
		main.addComponent(group);

		for (final Wine w : wines) {
			final NavigationButton c = new NavigationButton(w.getName());
			c.addStyleName(BaseTheme.BUTTON_LINK);
			group.addComponent(c);
			c.addClickListener(new NavigationButtonClickListener() {

				private static final long serialVersionUID = 8866920489370858445L;

				@Override
				public void buttonClick(NavigationButtonClickEvent event) {
					setCaption("List");
					presenter.wineSelected(w);
				}
			});
		}

		final Button search = new Button();
		search.setIcon(FontAwesome.SEARCH);
		getNavigationBar().setRightComponent(search);
	}

	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();

		// reset to long caption
		getNavigationBar().setCaption("Winecellar App");
	}

}
