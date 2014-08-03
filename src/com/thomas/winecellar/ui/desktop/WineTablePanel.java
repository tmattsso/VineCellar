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
	private final List<Wine> wines;

	private final boolean searchResults;

	public WineTablePanel(WinePresenter presenter, List<Wine> wines,
			boolean searchResults) {
		this.presenter = presenter;
		this.wines = wines;
		this.searchResults = searchResults;
		setSizeFull();

		setCaption("Winecellar app");
	}

	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();

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

		final Table t = new Table(null, container) {
			@Override
			protected String formatPropertyValue(Object rowId, Object colId,
					Property<?> property) {
				if (colId.equals("year")) {
					return property.getValue().toString();
				}
				return super.formatPropertyValue(rowId, colId, property);
			}
		};
		t.setSizeFull();

		t.setVisibleColumns("type", "name", "year", "region", "country",
				"producer", "amount", "drinkFrom", "drinkUntil", "drinkBest",
				"grapes", "comment");
		t.setColumnWidth("comment", 200);

		root.addComponent(t);

		t.setSelectable(true);
		t.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 5561240763809053670L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				presenter.wineSelected((Wine) t.getValue());
			}
		});
	}
}
