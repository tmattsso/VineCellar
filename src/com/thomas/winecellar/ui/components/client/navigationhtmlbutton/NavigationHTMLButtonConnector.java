package com.thomas.winecellar.ui.components.client.navigationhtmlbutton;

import com.thomas.winecellar.ui.components.NavigationHTMLButton;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationButtonConnector;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

@Connect(NavigationHTMLButton.class)
public class NavigationHTMLButtonConnector extends NavigationButtonConnector {

	private static final long serialVersionUID = 3314467124781721243L;

	public NavigationHTMLButtonConnector() {
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

		getWidget().setText(null);
		getWidget().getElement().setInnerHTML(getState().caption);

	}

}
