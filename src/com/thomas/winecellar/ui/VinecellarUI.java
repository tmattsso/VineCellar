package com.thomas.winecellar.ui;

import javax.servlet.annotation.WebServlet;

import com.thomas.winecellar.ComputerView;
import com.thomas.winecellar.ui.iphone.IphoneView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("winecellar")
@Widgetset("com.vaadin.addon.touchkit.gwt.TouchKitWidgetSet")
public class VinecellarUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VinecellarUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		final String agent = getPage().getWebBrowser().getBrowserApplication();

		if (agent.toLowerCase().contains("iphone")) {
			setContent(new IphoneView());
		} else {
			setContent(new ComputerView());
		}

	}

}