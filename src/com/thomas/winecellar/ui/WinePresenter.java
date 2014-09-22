package com.thomas.winecellar.ui;

import java.util.ArrayList;
import java.util.List;

import com.thomas.winecellar.data.Backend;
import com.thomas.winecellar.data.BackendException;
import com.thomas.winecellar.data.SearchTerms;
import com.thomas.winecellar.data.Wine;

public class WinePresenter {

	private WineView view;

	public void init(WineView view) {
		this.view = view;
		try {
			view.load(Backend.getWines(), false);
		} catch (final BackendException e) {
			view.showError("couldn't load wines!");
		}
	}

	public List<String> getProducers() {
		try {
			return Backend.getProducerList();
		} catch (final BackendException e) {
			view.showError("couldn't load producers");
		}
		return new ArrayList<String>();
	}

	public List<String> getCountries() {
		try {
			return Backend.getCountryList();
		} catch (final BackendException e) {
			view.showError("couldn't load countries");
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}

	public void wineSelected(Wine w) {
		if (w != null) {
			view.showDetails(w);
		}
	}

	public void setComment(Wine w, String comment) {
		w.setComment(comment);

		try {
			saveInBackend(w);
			view.showDetails(w);
		} catch (final BackendException e) {
			view.showError("Could not get regions");
		}
	}

	public void save(Wine w) {
		try {
			w = saveInBackend(w);
			view.showDetails(w);
		} catch (final BackendException e) {
			view.showError("Could not get regions");
		}
	}

	private Wine saveInBackend(Wine w) throws BackendException {
		return Backend.save(w);
	}

	public void addClicked() {
		view.showEdit(new Wine());
	}

	public void searchClicked() {
		view.showSearch();
	}

	public void handleError(Exception e) {
		e.printStackTrace();
		view.showError(e.getMessage());
	}

	public List<String> getRegions() {
		try {
			return Backend.getRegionList();
		} catch (final BackendException e) {
			view.showError("Could not get regions");
		}
		return new ArrayList<String>();
	}

	public void search(SearchTerms terms) {

		List<Wine> results;
		try {
			results = Backend.getWines(terms);
			view.load(results, true);
		} catch (final BackendException e) {
			view.showError("Could not search for wines");
		}
	}

	public void settingsClicked() {
		// TODO Auto-generated method stub

	}
}
