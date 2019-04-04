package com.mqt.engine.heuristics.flowshop;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.flowshop.FlowShopHeuristicDto;
import com.mqt.pojo.dto.flowshop.FlowShopInstanceDto;
import com.mqt.pojo.dto.flowshop.JobDto;
import com.mqt.pojo.dto.flowshop.SequenceDto;

/**
 * Module des Heuristiques pour les problèmes de Flow Shop avec permutation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/03/2019
 */
@Service("localSearchHeuristic")
public class LocalSearchHeuristic extends GenericFlowShopHeuristic {

	/**
	 * Constantes
	 */
	private final String NAME = "Multi-Neighborhood Local Search";

	/**
	 * Solve one instance by Local Search
	 * @param jobs
	 * @return
	 */
	public FlowShopHeuristicDto solve(FlowShopInstanceDto instance) {
		Integer nbrMachines = instance.getJobs().get(0).getProcessingTimes().size();		
		FlowShopHeuristicDto solution = buildFirstSolution(instance, nbrMachines, NAME);
		return this.solve(instance, solution);
	}

	/**
	 * Solve one instance by Local Search
	 * @param jobs
	 * @return
	 */
	public FlowShopHeuristicDto solve(FlowShopInstanceDto instance, FlowShopHeuristicDto solution) {
		Integer nbrMachines = instance.getJobs().get(0).getProcessingTimes().size();	
		boolean isBest = false;
		do {
			FlowShopHeuristicDto fistNeighborhood = searchFirstNeighborhood(solution, nbrMachines);
			if(null != fistNeighborhood) {
				solution = fistNeighborhood;
			} else {
				FlowShopHeuristicDto segondNeighborhood = searchSegondNeighborhood(solution, nbrMachines);
				if(null != segondNeighborhood) {
					solution = segondNeighborhood;
				} else {
					isBest = true;
				}
			}
		} while(!isBest);
		return solution;
	}
	
	/**
	 * Solve one instance by Local Search
	 * @param jobs
	 * @return
	 */
	public FlowShopHeuristicDto solveOnlyOneNeightborhood(FlowShopInstanceDto instance, FlowShopHeuristicDto solution) {
		Integer nbrMachines = instance.getJobs().get(0).getProcessingTimes().size();	
		boolean isBest = false;
		do {
			FlowShopHeuristicDto fistNeighborhood = searchFirstNeighborhood(solution, nbrMachines);
			if(null != fistNeighborhood) {
				solution = fistNeighborhood;
			} else {
				isBest = true;
			}
		} while(!isBest);
		return solution;
	}

	/**
	 * Recherche d'une meilleur solution en échangant une paire de jobs dans la séquence
	 * @param currentSolution
	 * @return
	 */
	private FlowShopHeuristicDto searchFirstNeighborhood(FlowShopHeuristicDto solution, Integer nbrMachines){		
		for(int i = 0; i<solution.getSequences().size()-1; i++) {
			for(int j = i+1; j<solution.getSequences().size(); j++) {				
				List<SequenceDto> s = clone(solution.getSequences());
				SequenceDto s1 = s.get(i);
				SequenceDto s2 = s.get(j);
				JobDto job = s1.getJob();
				s1.setJob(s2.getJob());
				s2.setJob(job);
				Double makespan = getMakespan(s, nbrMachines);
				if(makespan < solution.getOptimal()) {
					return new FlowShopHeuristicDto()
							.setName(NAME)
							.setOptimal(makespan)
							.setSequences(s);
				}
				
			}
		}
		return null;
	}

	/**
	 * Recherche d'une meilleur solution en échangant deux paires de jobs dans la séquence
	 * @param currentSolution
	 * @return
	 */
	private FlowShopHeuristicDto searchSegondNeighborhood(FlowShopHeuristicDto solution, Integer nbrMachines) {
		for (int i = 0; i < solution.getSequences().size() - 1; i++) {
			for (int j = i + 1; j < solution.getSequences().size(); j++) {
				for (int k = 0; k < solution.getSequences().size()-1; k++) {
					for (int l = k+1; l < solution.getSequences().size(); l++) {
						if(k!=i && k!=j && l!=i && l!=j) {
							List<SequenceDto> s = clone(solution.getSequences());
							SequenceDto s1 = s.get(i);
							SequenceDto s2 = s.get(j);
							SequenceDto s3 = s.get(k);
							SequenceDto s4 = s.get(l);
							JobDto job1 = s1.getJob();
							JobDto job2 = s3.getJob();
							s1.setJob(s2.getJob());
							s2.setJob(job1);
							s3.setJob(s4.getJob());
							s4.setJob(job2);
							Double makespan = getMakespan(s, nbrMachines);
							if (makespan < solution.getOptimal()) {
								return new FlowShopHeuristicDto().setName(NAME).setOptimal(makespan).setSequences(s);
							}
						}
					}
				}
			}
		}
		return null;
	}
}