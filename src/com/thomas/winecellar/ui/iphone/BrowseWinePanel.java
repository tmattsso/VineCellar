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
	private final List<Wine> wines;

	public BrowseWinePanel(List<Wine> wines, boolean searchResults,
			final WinePresenter presenter) {

		this.wines = wines;
		this.presenter = presenter;
		main = new VerticalLayout();
		setContent(main);

		updateTable(wines, searchResults);
		updateCaption();
	}

	private void updateCaption() {
		int numWinesInCellar = 0;

		for (final Wine w : wines) {
			if (w.getAmount() > 0) {
				numWinesInCellar += w.getAmount();
			}
		}

		getNavigationBar().setCaption(
				"Winecellar app (" + numWinesInCellar + ")");
	}

	public void updateTable(List<Wine> wines, boolean searchResults) {
		main.removeAllComponents();

		group = new VerticalComponentGroup();
		main.addComponent(group);

		for (final Wine w : wines) {
			final NavigationButton c = createWineButton(w);
			group.addComponent(c);
		}

		if (wines.isEmpty()) {
			final Label l = new Label("No wines found!");
			group.addComponent(l);
		}

		if (!searchResults) {
			setToolbar(new WineToolbar(presenter));
		}
	}

	private NavigationButton createWineButton(final Wine w) {
		final NavigationButton c = new NavigationButton(getWineCaption(w));
		c.setData(w);
		c.addStyleName(BaseTheme.BUTTON_LINK);
		c.addClickListener(new NavigationButtonClickListener() {

			private static final long serialVersionUID = 8866920489370858445L;

			@Override
			public void buttonClick(NavigationButtonClickEvent event) {
				setShortCaption();
				presenter.wineSelected(w);
			}
		});
		return c;
	}

	private String getWineCaption(Wine w) {
		return w.getName()
				+ (w.getYear() > 0 ? " (" + w.getYear() + ")" : " (NV)");
	}

	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();

		// reset to long caption
		updateCaption();
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

	public void setShortCaption() {
		getNavigationBar().setCaption("List");
	}

	public void updateWineInTable(Wine wineToUpdate) {
		final Iterator<Component> iterator = group.iterator();

		if (wines.contains(wineToUpdate)) {
			while (iterator.hasNext()) {
				final Component next = iterator.next();
				if (((AbstractComponent) next).getData().equals(wineToUpdate)) {
					next.setCaption(getWineCaption(wineToUpdate));
					break;
				}
			}
		} else {
			final NavigationButton c = createWineButton(wineToUpdate);
			group.addComponent(c, 0);
		}
	}

}
