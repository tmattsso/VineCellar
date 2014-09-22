package com.thomas.winecellar.ui;

import com.thomas.winecellar.data.Backend;
import com.thomas.winecellar.data.User;
import com.vaadin.addon.touchkit.ui.EmailField;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class LoginView extends VerticalLayout {

	private static final long serialVersionUID = 333537549844442031L;
	private EmailField emailField;
	private PasswordField passField;
	private Label errorLabel;
	private PasswordField pass2;

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

		emailField = new EmailField("Email:");
		emailField.focus();
		vl.addComponent(emailField);

		passField = new PasswordField("Password:");
		vl.addComponent(passField);

		errorLabel = new Label();
		errorLabel.addStyleName("error");
		errorLabel.setVisible(false);
		vl.addComponent(errorLabel);

		final Button login = new Button("Login");
		login.addClickListener(new LoginButtonListener());
		login.setClickShortcut(KeyCode.ENTER);
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

		emailField = new EmailField("E-mail:");
		vl.addComponent(emailField);

		passField = new PasswordField("Password");
		vl.addComponent(passField);

		pass2 = new PasswordField("Verify password:");
		vl.addComponent(pass2);

		final Button register = new Button("Finish registration");
		register.addClickListener(new FinishRegistrationButtonListener());
		addComponent(register);

		final Button cancel = new Button("Cancel");
		cancel.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -8286660897179364572L;

			@Override
			public void buttonClick(ClickEvent event) {
				buildLogin();
			}
		});
		cancel.addStyleName(BaseTheme.BUTTON_LINK);
		addComponent(cancel);
	}

	public void showError(String msg) {
		errorLabel.setVisible(true);
		errorLabel.setValue(msg);
	}

	private class RegisterButtonListener implements ClickListener {

		private static final long serialVersionUID = -5498575613828176659L;

		@Override
		public void buttonClick(ClickEvent event) {
			buildRegister();
		}

	}

	private class FinishRegistrationButtonListener implements ClickListener {

		private static final long serialVersionUID = -5498575613828176659L;

		@Override
		public void buttonClick(ClickEvent event) {

			emailField.addValidator(new EmailValidator("Not a valid email"));
			try {
				emailField.validate();
			} catch (final InvalidValueException e) {
				showError(e.getMessage());
				return;
			}

			if (passField.getValue() == null
					|| passField.getValue().equals(pass2.getValue())) {
				showError("Given passwords do not match");
				return;
			}

			final User registeredUser = Backend.register(emailField.getValue(),
					passField.getValue());
			VinecellarUI.login(registeredUser);
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
				showError("Wrong email or password");
			}
		}

	}

}
