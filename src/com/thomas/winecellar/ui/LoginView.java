package com.thomas.winecellar.ui;

import javax.servlet.http.Cookie;

import com.thomas.winecellar.data.Backend;
import com.thomas.winecellar.data.BackendException;
import com.thomas.winecellar.data.User;
import com.vaadin.addon.touchkit.ui.EmailField;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class LoginView extends VerticalLayout {

	private static final String COOKIE_NAME = "WinecellarUser";
	private static final long serialVersionUID = 333537549844442031L;
	private EmailField emailField;
	private NumberField passField;
	private Label errorLabel;
	private NumberField pass2;

	public LoginView() {
		setMargin(true);
		setSpacing(true);

		final Cookie user = checkCookieForUser();
		if (user != null) {
			buildPinLogin(user);
		} else {
			buildLogin();
		}
	}

	private void buildPinLogin(Cookie user) {
		removeAllComponents();

		final VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		addComponent(vl);
		setComponentAlignment(vl, Alignment.TOP_CENTER);

		final Label u = new Label("Welcome back, " + user.getValue()
				+ ". Please enter your PIN:");
		vl.addComponent(u);

		emailField = new EmailField("Email:");
		emailField.setValue(user.getValue());

		passField = new NumberField();
		passField.focus();
		passField.setWidth("100%");
		vl.addComponent(passField);

		if (!VinecellarUI.isMobile()) {
			vl.setWidth("50%");
		}

		errorLabel = new Label();
		errorLabel.addStyleName("error");
		errorLabel.setVisible(false);
		vl.addComponent(errorLabel);

		final Button login = new Button("Login");
		vl.addComponent(login);
		login.setClickShortcut(KeyCode.ENTER);
		login.addClickListener(new LoginButtonListener());

		final Button notUser = new Button("Not " + user.getValue() + "?");
		vl.addComponent(notUser);
		notUser.addStyleName(BaseTheme.BUTTON_LINK);
		notUser.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -8286660897179364572L;

			@Override
			public void buttonClick(ClickEvent event) {
				buildLogin();
			}
		});
	}

	private Cookie checkCookieForUser() {
		final Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				if (cookie.getName().equals(COOKIE_NAME)) {
					return cookie;
				}
			}
		}
		return null;
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

		passField = new NumberField("Password:");
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

		passField = new NumberField("Password");
		vl.addComponent(passField);

		pass2 = new NumberField("Verify password:");
		vl.addComponent(pass2);

		errorLabel = new Label();
		errorLabel.addStyleName("error");
		errorLabel.setVisible(false);
		vl.addComponent(errorLabel);

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

			final String value = passField.getValue();
			final String value2 = pass2.getValue();
			if (value == null || !value.equals(value2)) {
				showError("Given passwords do not match");
				return;
			}

			User registeredUser;
			try {
				registeredUser = Backend.register(emailField.getValue(), value);
				VinecellarUI.login(registeredUser);
			} catch (final BackendException e) {
				showError(e.getMessage());
			}
		}
	}

	private class LoginButtonListener implements ClickListener {

		private static final long serialVersionUID = 1682170430953020107L;

		@Override
		public void buttonClick(ClickEvent event) {
			final String email = emailField.getValue();
			final String pass = passField.getValue();
			User u = null;
			try {
				u = Backend.login(email, pass);
			} catch (final BackendException e) {
				System.err.println(e.getMessage());
			}

			if (u != null) {
				VinecellarUI.login(u);

				final Cookie myCookie = new Cookie(COOKIE_NAME, u.getEmail());
				myCookie.setMaxAge(60 * 60 * 24 * 7);
				myCookie.setPath(VaadinService.getCurrentRequest()
						.getContextPath());
				VaadinService.getCurrentResponse().addCookie(myCookie);

			} else {
				showError("Wrong email or password");
			}
		}

	}

}
