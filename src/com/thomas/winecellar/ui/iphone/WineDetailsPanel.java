package com.thomas.winecellar.ui.iphone;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.data.Wine.WineType;
import com.thomas.winecellar.ui.VinecellarUI;
import com.thomas.winecellar.ui.WinePresenter;
import com.thomas.winecellar.ui.components.Stepper;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class WineDetailsPanel extends NavigationView {

	private static final long serialVersionUID = 9055453044578390581L;
	private FieldGroup form;
	private final Wine wine;
	private Button editButton;

	public WineDetailsPanel(final Wine w, final WinePresenter presenter) {

		wine = w;
		setCaption("Edit wine");

		final VerticalLayout main = new VerticalLayout();
		main.setMargin(true);
		setContent(main);

		if (VinecellarUI.isMobile()) {
			main.setWidth("50%");
		}

		final VerticalComponentGroup root = new VerticalComponentGroup();
		main.addComponent(root);

		final BeanItem<Wine> item = new BeanItem<Wine>(w);
		form = new FieldGroup(item);

		form.setFieldFactory(new DefaultFieldGroupFieldFactory() {
			private static final long serialVersionUID = 1314980512470302629L;

			@SuppressWarnings("rawtypes")
			@Override
			public <T extends Field> T createField(Class<?> type,
					Class<T> fieldType) {

				final T f = super.createField(type, fieldType);
				if (f instanceof AbstractTextField) {
					((AbstractTextField) f).setNullRepresentation("");
				}
				return f;
			}
		});

		Field<?> field = form.buildAndBind("name");
		field.setCaption("Name");
		field.setRequired(true);
		field.setWidth("100%");
		root.addComponent(field);

		final ComboBox producer = new ComboBox("Producer");
		producer.setRequired(true);
		producer.addItems(presenter.getProducers());
		producer.setNullSelectionAllowed(false);
		producer.setNewItemsAllowed(true);
		form.bind(producer, "producer");
		producer.setWidth("100%");
		root.addComponent(producer);

		final NumberField year = new NumberField("Year");
		// year.setSelectionRange(1900, 2030);
		year.setMaxLength(4);
		year.setConverter(new WineYearNumberConverter());
		year.setInputPrompt("NV");
		form.bind(year, "year");
		year.setWidth("100%");
		year.setImmediate(true);
		year.setValidationVisible(false);
		root.addComponent(year);

		final ComboBox region = new ComboBox("Region");
		region.setRequired(true);
		region.addItems(presenter.getRegions());
		region.setNullSelectionAllowed(false);
		region.setNewItemsAllowed(true);
		form.bind(region, "region");
		region.setWidth("100%");
		root.addComponent(region);

		final ComboBox country = new ComboBox("Country");
		country.setRequired(true);
		country.addItems(presenter.getCountries());
		country.setNullSelectionAllowed(false);
		country.setNewItemsAllowed(true);
		form.bind(country, "country");
		country.setWidth("100%");
		root.addComponent(country);

		final NativeSelect type = new NativeSelect("Type");
		type.setRequired(true);
		type.setNullSelectionAllowed(false);
		for (final WineType t : WineType.values()) {
			type.addItem(t);
		}
		form.bind(type, "type");
		type.setWidth("100%");
		root.addComponent(type);

		field = form.buildAndBind("grapes");
		field.setCaption("Grapes");
		field.setWidth("100%");
		root.addComponent(field);

		final Stepper amount = new Stepper(0);
		amount.setCaption("Amount");
		form.bind(amount, "amount");
		root.addComponent(amount);

		final TextArea comment = new TextArea("Comments");
		comment.setNullRepresentation("");
		form.bind(comment, "comment");
		comment.setWidth("100%");
		comment.setHeight(null);
		root.addComponent(comment);

		field = form.buildAndBind("drinkFrom");
		field.setCaption("Drink from");
		field.setWidth("100%");
		root.addComponent(field);

		field = form.buildAndBind("drinkUntil");
		field.setCaption("Drink before");
		field.setWidth("100%");
		root.addComponent(field);

		field = form.buildAndBind("drinkBest");
		field.setCaption("At best");
		field.setWidth("100%");
		root.addComponent(field);

		editButton = new Button("Save");
		editButton.setIcon(FontAwesome.CHECK);
		setToolbar(editButton);

		editButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 738090683647084973L;

			@Override
			public void buttonClick(ClickEvent event) {

				// check presence of all fields manually first
				for (final Field<?> f : form.getFields()) {
					try {
						f.validate();
					} catch (final InvalidValueException e) {
						if (e instanceof EmptyValueException) {
							Notification.show(
									"Please fill all required fields ("
											+ f.getCaption() + ")",
									Type.WARNING_MESSAGE);
							return;
						} else {
							presenter.handleError(e);
							return;
						}
					}
				}

				try {
					form.commit();
					presenter.save(wine);
					// TODO remove unnecessary navigation
				} catch (final CommitException e) {
					presenter.handleError(e);
				}
			}
		});

	}

}
