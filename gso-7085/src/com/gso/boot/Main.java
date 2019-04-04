package com.gso.boot;

import com.gso.engines.MultiNeighborhoodSearchEngin;
import com.gso.engines.OptimalSchedulingEngineV2;
import com.gso.engines.OptimalSchedulingEngineV3;
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

		// 2eme version de l'engin (avec calcul des temps de mouvements)
		OptimalSchedulingEngineV2.getEngine().solve(ParsingFileEngine.BuildInstance("instance_4_2.xlsx"));

		// 3eme version de l'engin (dynamique avec historique et déchargement)
		OptimalSchedulingEngineV3.getEngine().solve(ParsingFileEngine.BuildInstance("instance_dynamique.xlsx"));
		
		// Résolution à travers l'heuristique
		MultiNeighborhoodSearchEngin.getEngine().solve(ParsingFileEngine.BuildInstance("instance_4_2.xlsx"), 0.3, 0.2);
	}
}
