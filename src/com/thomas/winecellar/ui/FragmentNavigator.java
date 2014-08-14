package com.thomas.winecellar.ui;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.ui.UI;

public class FragmentNavigator extends NavigationManager {

	private static final long serialVersionUID = -2190827696952996701L;

	private boolean maskURIChange = false;

	{
		addNavigationListener(new NavigationListener() {

			private static final long serialVersionUID = -4073678099862664340L;

			@Override
			public void navigate(NavigationEvent event) {
				maskURIChange = true;
				final int newId = getCurrentComponent().hashCode();
				UI.getCurrent().getPage().setUriFragment(newId + "");
				maskURIChange = false;
			}
		});

		Page.getCurrent().addUriFragmentChangedListener(
				new UriFragmentChangedListener() {

					private static final long serialVersionUID = -1713918099181690469L;

					@Override
					public void uriFragmentChanged(UriFragmentChangedEvent event) {

						if (maskURIChange) {
							return;
						}

						maskURIChange = true;
						// user pressed back or forward; determine which
						try {
							final int id = Integer.parseInt(event
									.getUriFragment());

							if (getNextComponent() != null
									&& getNextComponent().hashCode() == id) {
								navigateTo(getNextComponent());
							} else if (getPreviousComponent() != null
									&& getPreviousComponent().hashCode() == id) {
								navigateBack();
							}
						} catch (final Exception e) {
							// ignore
							e.printStackTrace();
						}
						maskURIChange = false;
					}
				});
	}
}
