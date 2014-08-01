package com.thomas.winecellar.ui.iphone;

import com.thomas.winecellar.data.SearchTerms;
import com.thomas.winecellar.data.Wine.WineType;
import com.thomas.winecellar.ui.WinePresenter;
import com.thomas.winecellar.util.ParseUtil;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SearchPanel extends NavigationView {

	private static final long serialVersionUID = -19615011146276478L;

	public SearchPanel(final WinePresenter presenter) {

		final VerticalLayout root = new VerticalLayout();
		root.setMargin(true);
		setContent(root);

		setCaption("Search");

		final TextField searchTerms = new TextField("Text Search");
		searchTerms.setWidth("100%");
		root.addComponent(searchTerms);

		final NativeSelect type = new NativeSelect("Type");
		for (final WineType t : WineType.values()) {
			type.addItem(t);
		}
		type.setWidth("100%");
		root.addComponent(type);

		final NativeSelect producer = new NativeSelect("Producer",
				presenter.getProducers());
		producer.setWidth("100%");
		root.addComponent(producer);

		final NativeSelect area = new NativeSelect("Region",
				presenter.getRegions());
		area.setWidth("100%");
		root.addComponent(area);
		final NativeSelect country = new NativeSelect("Country",
				presenter.getCountries());
		country.setWidth("100%");
		root.addComponent(country);

		final HorizontalLayout hl = new HorizontalLayout();
		hl.setCaption("Year");
		root.addComponent(hl);
		hl.setWidth("100%");

		final NumberField min = new NumberField();
		// min.setSelectionRange(1900, 2030);
		min.setMaxLength(4);
		min.setWidth(5, Unit.EM);
		hl.addComponent(min);

		final Label to = new Label("to");
		to.setSizeUndefined();
		hl.addComponent(to);
		hl.setComponentAlignment(to, Alignment.MIDDLE_CENTER);
		hl.setExpandRatio(to, 1);

		final NumberField max = new NumberField();
		// max.setSelectionRange(1900, 2030);
		max.setMaxLength(4);
		max.setWidth(5, Unit.EM);
		hl.addComponent(max);

		final Button b = new Button("Get results");
		b.setIcon(FontAwesome.SEARCH);
		setToolbar(b);

		b.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -9157386958818505542L;

			@Override
			public void buttonClick(ClickEvent event) {

				final SearchTerms terms = new SearchTerms();
				terms.text = searchTerms.getValue();
				terms.country = (String) country.getValue();
				terms.region = (String) area.getValue();
				terms.producer = (String) producer.getValue();
				terms.type = (WineType) type.getValue();

				if (min.getValue() != null && min.getValue().length() > 1) {
					terms.yearmin = ParseUtil.getInt(min.getValue());
				}
				if (max.getValue() != null && max.getValue().length() > 1) {
					terms.yearmax = ParseUtil.getInt(max.getValue());
				}

				presenter.search(terms);
			}
		});
	}
}
