package com.thomas.winecellar.ui;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.thomas.winecellar.ui.iphone.IphoneView;
import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.settings.TouchKitSettings;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("winecellar")
@Widgetset("com.vaadin.addon.touchkit.gwt.TouchKitWidgetSet")
public class VinecellarUI extends UI {

	@WebServlet(value = "/*")
	@VaadinServletConfiguration(productionMode = false, ui = VinecellarUI.class)
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

		final String agent = getPage().getWebBrowser().getBrowserApplication();
		System.out.println(agent);

		getPage().setTitle("WineCellar");

		if (agent.toLowerCase().contains("iphone")
				|| agent.contains("GT-I9300")) {
			setContent(new IphoneView());
		} else {
			setContent(new ComputerView());
		}

	}

}