package com.gso.model;

import java.util.ArrayList;
import java.util.List;

import ilog.concert.IloNumVar;
import ilog.concert.IloRange;

/**
 * Classe représentant notre modèle de précédence à résoudre
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @author Safwan Al-Shabakji
 * @since 10/03/2019
 * @version 1.0
 */
public class Model {
	public final Integer nbrLoadStations = 3;
	public final Integer nbrModes = 3;
	
	public List<Job> jobs = new ArrayList<Job>();
	public Integer nbrJobs;
	public Double I = 0.0;
	
	public IloNumVar[][] varLoad;
	public IloNumVar[][] varBegin;
	public IloNumVar[][] varWeld;
	public IloNumVar[][][] varMode;
	public IloNumVar[][][][] varPrecedence;
	public IloNumVar[] varDelay;

	public IloRange[][][][] C1;
	public IloRange[][] C2;
	public IloRange[][][][] C3;
	public IloRange[][][][] C4;
	public IloRange[][][][] C5;
	public IloRange[] C6;
	public IloRange[][] C7;
	public IloRange[][] C8;
	public IloRange[] C9;
	public IloRange[] C10;
	public IloRange[][][][] C11;
	public IloRange[][][][][][] C12;
	public IloRange[] C13;
	public IloRange[][] C14;
	public IloRange[][][][] C15;

	/**
	 * Constructeur à partir d'un probleme
	 * @param problem
	 */
	public Model(Instance problem) {
		this.jobs = problem.getJobs();
		nbrJobs = this.jobs.size();
		
		// Calcul d'une borne superieur
		for(Job j : jobs) {
			for(Operation o : j.getOperations()) {
				I += o.getProcessingTime() + j.getPositionTime();
			}
		}
		I *= 1.5;

		varLoad = new IloNumVar[nbrJobs][nbrLoadStations];
		varBegin = new IloNumVar[nbrJobs][nbrLoadStations];
		varWeld = new IloNumVar[nbrJobs][];
		varMode = new IloNumVar[nbrJobs][][];
		varPrecedence = new IloNumVar[nbrJobs][][][];
		varDelay = new IloNumVar[nbrJobs];

		C1 = new IloRange[nbrJobs][][][];
		C2 = new IloRange[nbrJobs][];
		C3 = new IloRange[nbrJobs][][][];
		C4 = new IloRange[nbrJobs][][][];
		C5 = new IloRange[nbrJobs][][][];
		C6 = new IloRange[nbrJobs];
		C7 = new IloRange[nbrJobs][];
		C8 = new IloRange[nbrJobs][];
		C9 = new IloRange[nbrJobs];
		C10 = new IloRange[nbrJobs];
		C11 = new IloRange[nbrJobs][][][];
		C12 = new IloRange[nbrJobs][][][][][];
		C13 = new IloRange[nbrJobs];
		C14 = new IloRange[nbrJobs][];
		C15 = new IloRange[nbrJobs][][][];
	}
}
