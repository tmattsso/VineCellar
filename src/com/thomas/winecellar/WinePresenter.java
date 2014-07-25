package com.thomas.winecellar;

import java.util.List;

import com.thomas.winecellar.data.Wine;

public class WinePresenter {

	private WineView view;

	public void init(WineView view) {
		this.view = view;
	}

	public List<String> getProducers() {
		return null;
	}

	public List<String> getCountries() {
		return null;
	}

	public void wineSelected(Wine w) {
		view.showDetails(w);
	}

	public void setComment(Wine w, String comment) {
		w.setComment(comment);

		save(w);

		view.showDetails(w);
	}

	public void add(Wine w) {
		w = save(w);
		view.showDetails(w);
	}

	private Wine save(Wine w) {
		// TODO Auto-generated method stub
		return null;
	}
}
