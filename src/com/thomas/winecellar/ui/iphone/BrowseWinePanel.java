package com.thomas.winecellar.ui.iphone;

import java.util.Iterator;
import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.ui.WinePresenter;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class BrowseWinePanel extends NavigationView {

	private static final long serialVersionUID = 3298846700229147335L;
	private final VerticalLayout main;
	private final WinePresenter presenter;
	private VerticalComponentGroup group;

	public BrowseWinePanel(List<Wine> wines, boolean searchResults,
			final WinePresenter presenter) {

		this.presenter = presenter;
		main = new VerticalLayout();
		setContent(main);

		updateTable(wines, searchResults);
	}

	@Override
	public void attach() {
		super.attach();

	}

	public void updateTable(List<Wine> wines, boolean searchResults) {
		main.removeAllComponents();

		group = new VerticalComponentGroup();
		main.addComponent(group);

		for (final Wine w : wines) {
			final NavigationButton c = new NavigationButton(w.getName() + " ("
					+ w.getYear() + ")");
			c.setData(w);
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

		if (wines.isEmpty()) {
			final Label l = new Label("No wines found!");
			group.addComponent(l);
		}

		if (!searchResults) {
			setToolbar(new WineToolbar(presenter));
		}
	}

	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();

		// reset to long caption
		getNavigationBar().setCaption("Winecellar App");
	}

	public void scrollTo(Wine selectedWine) {

		final int elementSize = 43;// px

		int numElements = 0;
		boolean hasElement = false;

		final Iterator<Component> iterator = group.iterator();
		while (iterator.hasNext()) {
			final Component next = iterator.next();
			if (((AbstractComponent) next).getData().equals(selectedWine)) {
				hasElement = true;
				break;
			}
			numElements++;
		}

		// needs some trickery
		if (hasElement) {
			setScrollPosition(numElements * elementSize);
		}
	}

}
