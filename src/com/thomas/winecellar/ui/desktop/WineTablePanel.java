package com.thomas.winecellar.ui.desktop;

import java.util.List;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.ui.WinePresenter;
import com.thomas.winecellar.ui.iphone.WineToolbar;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class WineTablePanel extends NavigationView {

	private static final long serialVersionUID = -1355514154334927278L;

	private final WinePresenter presenter;

	private Table wineTable;

	public WineTablePanel(WinePresenter presenter, List<Wine> wines,
			boolean searchResults) {
		this.presenter = presenter;
		setSizeFull();

		updateTable(wines, searchResults);

		updateCaption(wines);
	}

	private void updateCaption(List<Wine> wines) {
		int numWinesInCellar = 0;

		for (final Wine w : wines) {
			if (w.getAmount() > 0) {
				numWinesInCellar += w.getAmount();
			}
		}

		setCaption("Winecellar app (" + numWinesInCellar + ")");
	}

	public void updateTable(List<Wine> wines, boolean searchResults) {
		final VerticalLayout root = new VerticalLayout();
		setContent(root);
		root.setMargin(true);
		root.setSizeFull();

		if (!searchResults) {
			setToolbar(new WineToolbar(presenter));
		}

		if (wines.isEmpty()) {
			final Label l = new Label("No wines found!");
			root.addComponent(l);

			return;
		}

		final BeanItemContainer<Wine> container = new BeanItemContainer<Wine>(
				Wine.class, wines);

		wineTable = new Table(null, container) {
			private static final long serialVersionUID = 5646119148734072213L;

			@Override
			protected String formatPropertyValue(Object rowId, Object colId,
					Property<?> property) {
				if (colId.equals("year")) {

					final Object value = property.getValue();
					if (value.equals(0)) {
						return "NV";
					}
					return value.toString();
				}
				return super.formatPropertyValue(rowId, colId, property);
			}
		};
		wineTable.setSizeFull();

		wineTable.setVisibleColumns("type", "name", "year", "region",
				"country", "producer", "amount", "drinkFrom", "drinkUntil",
				"drinkBest", "grapes", "comment");
		wineTable.setColumnWidth("comment", 200);

		root.addComponent(wineTable);

		wineTable.setSelectable(true);
		wineTable.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 5561240763809053670L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				presenter.wineSelected((Wine) wineTable.getValue());
			}
		});
	}

	public void scrollTo(Wine selectedWine) {
		wineTable.setCurrentPageFirstItemId(selectedWine);
	}
}
