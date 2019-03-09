package com.gso.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant notre modèle de données
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @author Safwan Al-Shabakji 
 * @since 07/03/2019
 * @version 1.0
 */
public class BombardierWeldingCellInstance {
	private List<Job> jobs = new ArrayList<Job>();

	/**
	 * @return the jobs
	 */
	public List<Job> getJobs() {
		return jobs;
	}

	/**
	 * @param jobs the jobs to set
	 */
	public BombardierWeldingCellInstance setJobs(List<Job> jobs) {
		this.jobs = jobs;
		return this;
	}
}
