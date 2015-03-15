package com.thomas.winecellar.ui.iphone;

import com.thomas.winecellar.ui.VinecellarUI;
import com.thomas.winecellar.ui.WinePresenter;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class ChangePinPanel extends NavigationView {

	public ChangePinPanel(final WinePresenter presenter) {

		final VerticalLayout root = new VerticalLayout();
		root.setMargin(true);
		root.setSpacing(true);
		setContent(root);

		setCaption("Change PIN:");

		final NumberField current = new NumberField("Current PIN");
		current.setWidth("100%");
		root.addComponent(current);

		final NumberField new1 = new NumberField("New PIN");
		new1.setWidth("100%");
		root.addComponent(new1);

		final NumberField new2 = new NumberField("New PIN");
		new2.setWidth("100%");
		root.addComponent(new2);

		final Button b = new Button("Change");
		root.addComponent(b);
		b.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				if (new1.getValue() != null
						&& new1.getValue().equals(new2.getValue())) {
					presenter.changePin(VinecellarUI.getUser(),
							current.getValue(), new1.getValue());
				}
			}
		});
	}
}
