package com.gso.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la gestion des stations durant l'éxecution du programme
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/04/2019
 * @version 1.0
 */
public class StationManager {
	private List<Double> loadingStations = new ArrayList<Double>();

	/**
	 * Create a new manager
	 * @return
	 */
	public static StationManager createManager() {
		StationManager m = new StationManager();
		m.loadingStations.add(new Double(0.0));
		m.loadingStations.add(new Double(0.0));
		m.loadingStations.add(new Double(0.0));
		return m;
	}

	/**
	 * Un job souhaite entrer dans une station
	 * @return
	 */
	public void enterStation(CeduledJob job, Double LT) {
		int station = 1;
		Double dateLoad = loadingStations.get(1);
		if(!job.isSize()) {
			for(Double s : loadingStations) {
				if(s < dateLoad) {
					station = loadingStations.indexOf(s);
					dateLoad = s;
				}
			}
		}
		job.setLoadedDate(dateLoad + LT);
		job.setLoadedStation(station);
	}

	/**
	 * Liberer une station de chargement à une date précise
	 * @param job
	 * @param date
	 * @param station
	 */
	public void leaveStation(CeduledJob job, Double date, Integer station, Double LT) {
		job.setEndDate(date + LT);
		this.loadingStations.set(station, date + LT);
	}
}