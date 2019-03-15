package com.gso.boot;

import com.gso.engines.OptimalSchedulingEngine;
import com.gso.engines.OptimalSchedulingEngineV2;
import com.gso.engines.ParsingFileEngine;

/**
 * Classe principale du projet
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @author Safwan Al-Shabakji
 * @since 08/03/2019
 * @version 1.0
 */
public class Main {

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		
		// 1ere version de l'engin
		OptimalSchedulingEngine.getEngine().solve(ParsingFileEngine.BuildInstance("instance_5.xlsx"));
		OptimalSchedulingEngine.getEngine().solve(ParsingFileEngine.BuildInstance("instance_4.xlsx"));
		OptimalSchedulingEngine.getEngine().solve(ParsingFileEngine.BuildInstance("instance_2.xlsx"));

		// 2eme version de l'engin (avec calcul des temps de mouvements)
		OptimalSchedulingEngineV2.getEngine().solve(ParsingFileEngine.BuildInstance("instance_3.xlsx"));
	}
}
