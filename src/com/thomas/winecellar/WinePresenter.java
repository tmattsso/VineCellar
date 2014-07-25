package com.thomas.winecellar;

import java.sql.SQLException;
import java.util.List;

import com.thomas.winecellar.data.Backend;
import com.thomas.winecellar.data.Wine;

public class WinePresenter {

	private WineView view;

	public void init(WineView view) {
		this.view = view;
		view.load(Backend.getWines());
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

		try {
			save(w);
			view.showDetails(w);
		} catch (final SQLException e) {
			e.printStackTrace();
			view.showError(e);
		}
	}

	public void add(Wine w) {
		try {
			w = save(w);
			view.showDetails(w);
		} catch (final SQLException e) {
			e.printStackTrace();
			view.showError(e);
		}
	}

	private Wine save(Wine w) throws SQLException {
		return Backend.save(w);
	}
}
