package com.thomas.winecellar.ui;

import com.thomas.winecellar.data.Backend;
import com.thomas.winecellar.data.User;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class LoginView extends VerticalLayout {

	private static final long serialVersionUID = 333537549844442031L;
	private TextField emailField;
	private PasswordField passField;
	private Label errorLabel;

	public LoginView() {
		setMargin(true);
		setSpacing(true);
		buildLogin();
	}

	private void buildLogin() {
		removeAllComponents();

		final Label caption = new Label("Welcome to the WineCellar App!");
		addComponent(caption);

		final VerticalComponentGroup vl = new VerticalComponentGroup();
		addComponent(vl);

		emailField = new TextField("Email:");
		vl.addComponent(emailField);

		passField = new PasswordField("Password:");
		vl.addComponent(passField);

		errorLabel = new Label("Wrong email or password");
		errorLabel.addStyleName("error");
		errorLabel.setVisible(false);
		vl.addComponent(errorLabel);

		final Button login = new Button("Login");
		login.addClickListener(new LoginButtonListener());
		addComponent(login);

		final Button register = new Button("Register");
		register.addClickListener(new RegisterButtonListener());
		register.addStyleName(BaseTheme.BUTTON_LINK);
		addComponent(register);
	}

	private void buildRegister() {
		removeAllComponents();

		final VerticalComponentGroup vl = new VerticalComponentGroup();
		addComponent(vl);

		final TextField email = new TextField();
		vl.addComponent(email);

		final PasswordField pass = new PasswordField();
		vl.addComponent(pass);

		final Button login = new Button("Login");
		login.addClickListener(new LoginButtonListener());
		vl.addComponent(login);

		final Button register = new Button("Register");
		register.addClickListener(new RegisterButtonListener());
		register.addStyleName(BaseTheme.BUTTON_LINK);
		addComponent(register);
	}

	private void showLoginError() {
		errorLabel.setVisible(true);
	}

	private class RegisterButtonListener implements ClickListener {

		private static final long serialVersionUID = -5498575613828176659L;

		@Override
		public void buttonClick(ClickEvent event) {
			buildRegister();
		}

	}

	private class LoginButtonListener implements ClickListener {

		private static final long serialVersionUID = 1682170430953020107L;

		@Override
		public void buttonClick(ClickEvent event) {
			final String email = emailField.getValue();
			final String pass = passField.getValue();
			final User u = Backend.login(email, pass);

			if (u != null) {
				VinecellarUI.login(u);
			} else {
				showLoginError();
			}
		}

	}

}
