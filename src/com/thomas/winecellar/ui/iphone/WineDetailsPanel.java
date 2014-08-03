package com.thomas.winecellar.ui.iphone;

import java.util.Locale;

import com.thomas.winecellar.data.Wine;
import com.thomas.winecellar.data.Wine.WineType;
import com.thomas.winecellar.ui.Stepper;
import com.thomas.winecellar.ui.WinePresenter;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class WineDetailsPanel extends NavigationView {

	private static final long serialVersionUID = 9055453044578390581L;
	private FieldGroup form;
	private final Wine wine;
	private Button editButton;
	private boolean editMode;

	public WineDetailsPanel(final Wine w, final WinePresenter presenter,
			boolean edit) {

		wine = w;
		editMode = edit;
		setCaption("Edit wine");

		final VerticalLayout main = new VerticalLayout();
		main.setMargin(true);
		setContent(main);

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
		field.setRequired(true);
		field.setWidth("100%");
		root.addComponent(field);

		final ComboBox producer = new ComboBox("Producer");
		producer.setRequired(true);
		producer.addItems(presenter.getProducers());
		producer.setNullSelectionAllowed(false);
		producer.setNewItemsAllowed(true);
		producer.setNewItemHandler(new NewItemHandler() {

			private static final long serialVersionUID = 567953829674037166L;

			@Override
			public void addNewItem(String newItemCaption) {
				producer.addItem(newItemCaption);
				producer.select(newItemCaption);
			}
		});
		form.bind(producer, "producer");
		producer.setWidth("100%");
		producer.setRequired(true);
		root.addComponent(producer);

		final NumberField year = new NumberField("Year");
		// year.setSelectionRange(1900, 2030);
		year.setMaxLength(4);
		year.setRequired(true);
		form.bind(year, "year");
		year.setConverter(new StringToIntegerConverter() {
			private static final long serialVersionUID = 6742590457370097026L;

			@Override
			public String convertToPresentation(Integer value,
					Class<? extends String> targetType, Locale locale)
							throws com.vaadin.data.util.converter.Converter.ConversionException {
				return value + "";
			}
		});
		year.setWidth("100%");
		if (editMode && wine.getYear() < 1900) {
			year.setValue("2010");
		}
		root.addComponent(year);

		final ComboBox region = new ComboBox("Region");
		region.setRequired(true);
		region.addItems(presenter.getRegions());
		region.setNullSelectionAllowed(false);
		region.setNewItemsAllowed(true);
		region.setNewItemHandler(new NewItemHandler() {

			private static final long serialVersionUID = 567953829674037166L;

			@Override
			public void addNewItem(String newItemCaption) {
				region.addItem(newItemCaption);
				region.select(newItemCaption);
			}
		});
		form.bind(region, "region");
		region.setWidth("100%");
		root.addComponent(region);

		final ComboBox country = new ComboBox("Country");
		country.setRequired(true);
		country.addItems(presenter.getCountries());
		country.setNullSelectionAllowed(false);
		country.setNewItemsAllowed(true);
		country.setNewItemHandler(new NewItemHandler() {

			private static final long serialVersionUID = 567953829674037166L;

			@Override
			public void addNewItem(String newItemCaption) {
				country.addItem(newItemCaption);
				country.select(newItemCaption);
			}
		});
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

		editButton = new Button("Edit");
		editButton.setIcon(FontAwesome.PENCIL);
		setToolbar(editButton);

		editButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 738090683647084973L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (editMode) {
					try {
						form.commit();
						presenter.save(wine);
						// TODO remove unnecessary navigation
					} catch (final CommitException e) {
						presenter.handleError(e);
					}
				} else {
					edit();
				}
			}
		});

		if (edit) {
			edit();
		} else {
			form.setReadOnly(true);
		}
	}

	private void edit() {

		editMode = true;

		form.setReadOnly(false);

		editButton.setCaption("Save");
		editButton.setIcon(FontAwesome.CHECK);
	}
}
