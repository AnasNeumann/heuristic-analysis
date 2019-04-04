package com.mqt.engine.heuristics.flowshop;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.flowshop.FlowShopHeuristicDto;
import com.mqt.pojo.dto.flowshop.FlowShopInstanceDto;
import com.mqt.pojo.dto.flowshop.JobDto;
import com.mqt.pojo.dto.flowshop.SequenceDto;

/**
 * Module des Heuristiques pour les problèmes de Flow Shop avec permutation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 26/03/2019
 */
@Service("simulatedAnnealingHeuristic")
public class SimulatedAnnealingHeuristic extends GenericFlowShopHeuristic  {

	/**
	 * Constantes
	 */
	private final Double  F     = 0.025;
	private final Double  alpha = 0.25;
	private final Integer Nt1    = 1500;
	private final Integer Nt2    = 4500;
	private final String  NAME  = "Simulated Annealing";

	/**
	 * Solve one instance by Local Search
	 * @param instance
	 * @param solution
	 * @param temperature
	 * @return
	 */
	public FlowShopHeuristicDto solve(FlowShopInstanceDto instance, FlowShopHeuristicDto solution, Double temperature) {
		Integer nbrMachines = instance.getJobs().get(0).getProcessingTimes().size();	
		Random generator = new Random();
		while(temperature > F) {
			int Nt = (temperature >= 1 && temperature <= 15)? Nt2 : Nt1;
			for(int n=0; n<Nt; n++) {
				FlowShopHeuristicDto newSolution = searchNeighborhood(solution, nbrMachines);
				Double delta = newSolution.getOptimal() - solution.getOptimal();
				Double x = generator.nextDouble();
				if(delta <= 0 || x < Math.exp(-delta/temperature)) {
					solution = newSolution;
				}
			}
			temperature *= alpha;
		}
		return solution.setName(NAME);
	}
	
	/**
	 * Recherche d'une solution dans le voisinage
	 * @param solution
	 * @param nbrMachines
	 * @return
	 */
	private FlowShopHeuristicDto searchNeighborhood(FlowShopHeuristicDto solution, Integer nbrMachines){		
		Random generator = new Random();
		int i,j;
		do {
			i = generator.nextInt(solution.getSequences().size());
			j = generator.nextInt(solution.getSequences().size());
			if(i != j) {
				List<SequenceDto> s = clone(solution.getSequences());
				SequenceDto s1 = s.get(i);
				SequenceDto s2 = s.get(j);
				JobDto job = s1.getJob();
				s1.setJob(s2.getJob());
				s2.setJob(job);
				Double makespan = getMakespan(s, nbrMachines);
				return new FlowShopHeuristicDto()
						.setOptimal(makespan)
						.setSequences(s);
			}
		} while(i == j);
		return null;
	}

}