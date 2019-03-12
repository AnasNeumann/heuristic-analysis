package com.gso.boot;

import com.gso.engines.OptimalSchedulingEngine;
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
		OptimalSchedulingEngine.getEngine().solve(ParsingFileEngine.BuildInstance("instance_4.xlsx"));
	}
}
