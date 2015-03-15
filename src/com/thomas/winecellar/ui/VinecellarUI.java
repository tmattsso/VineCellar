package com.thomas.winecellar.ui;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.thomas.winecellar.data.User;
import com.thomas.winecellar.ui.desktop.ComputerView;
import com.thomas.winecellar.ui.iphone.IphoneView;
import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.settings.TouchKitSettings;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("winecellar")
public class VinecellarUI extends UI {

	private boolean isMobile;
	private User user;

	@WebServlet(value = "/*")
	@VaadinServletConfiguration(productionMode = false, ui = VinecellarUI.class, widgetset = "com.thomas.winecellar.widgetset.VinecellarWidgetset")
	public static class Servlet extends TouchKitServlet {

		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();

			final TouchKitSettings s = getTouchKitSettings();

			s.getWebAppSettings().setWebAppCapable(true);
			s.getWebAppSettings().setStatusBarStyle("black");

			final String contextPath = getServletConfig().getServletContext()
					.getContextPath();
			final String path = contextPath
					+ "/VAADIN/themes/winecellar/icons/exec_wine.png";
			s.getApplicationIcons().addApplicationIcon(path);

			s.getApplicationCacheSettings().setCacheManifestEnabled(true);

		}
	}

	@Override
	protected void init(VaadinRequest request) {

		final int windowWidth = getPage().getBrowserWindowWidth();

		// most phones have smaller viewport than this.
		isMobile = windowWidth <= 720;

		getPage().setTitle("WineCellar App");

		setContent(new LoginView());
	}

	public static void login(User u) {

		if (u == null) {
			return;
		}

		final VinecellarUI ui = (VinecellarUI) getCurrent();

		ui.user = u;
		if (VinecellarUI.isMobile()) {
			ui.setContent(new IphoneView());
		} else {
			ui.setContent(new ComputerView());
		}
	}

	public static boolean isMobile() {
		return ((VinecellarUI) getCurrent()).isMobile;
	}

	public static User getUser() {
		return ((VinecellarUI) getCurrent()).user;
	}

}