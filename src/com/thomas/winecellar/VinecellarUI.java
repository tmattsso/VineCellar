package com.thomas.winecellar;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("winecellar")
public class VinecellarUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VinecellarUI.class, widgetset = "com.example.vinecellar.widgetset.VinecellarWidgetset")
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