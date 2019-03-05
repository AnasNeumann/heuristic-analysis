package com.mqt.engine.heuristics.flowshop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.flowshop.FlowShopHeuristicDto;
import com.mqt.pojo.dto.flowshop.FlowShopInstanceDto;
import com.mqt.pojo.dto.flowshop.JobDto;
import com.mqt.pojo.dto.flowshop.SequenceDto;

/**
 * Module des Heuristiques pour les problèmes de Flow Shop avec permutation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/03/2019
 */
@Service("cdsHeuristic")
public class CDSHeuristic extends GenericFlowShopHeuristic {

	/**
	 * Solve one instance by CDS (solve all 2-machines subproblems)
	 * @param instance
	 * @return
	 */
	public FlowShopHeuristicDto solve(FlowShopInstanceDto instance) {
		Integer nbrMachines = instance.getJobs().get(0).getProcessingTimes().size();
		List<SequenceDto> bestSequences = generateBestSequence(instance.getJobs(), nbrMachines);
		return new FlowShopHeuristicDto().setName("CDS : Campbell, Dudeck & Smith").setOptimal(getMakespan(bestSequences, nbrMachines)).setSequences(bestSequences);
	}

	/**
	 * Reconstructe a sequence from the two machine subsequence
	 * @param twoMachineSequences
	 * @return
	 */
	private List<SequenceDto> reconstructSequence(List<SequenceDto> twoMachineSequences, List<JobDto> jobs){
		List<SequenceDto> result = new ArrayList<SequenceDto>();
		for(SequenceDto s : twoMachineSequences) {
			for(JobDto j : jobs) {
				if(j.getId().equals(s.getJob().getId())) {
					result.add(new SequenceDto().setJob(j));
				}
			}
		}
		return result;
	}
	
	/**
	 * Generate all subproblems to solve with johnson
	 * @param instance
	 * @param totalMachines
	 * @return
	 */
	private List<SequenceDto> generateBestSequence(List<JobDto> jobs, Integer totalMachines) {
		Double bestSolution = null;
		List<SequenceDto> bestSequences = new ArrayList<SequenceDto>();
		for(int i=1; i<totalMachines; i++) {
			List<JobDto> problem = generateOneSubProblem(jobs, i);
			List<SequenceDto> currentSequence = johnson(problem);
			Double currentSolution = getMakespan(currentSequence, 2);
			if(null == bestSolution ||  currentSolution < bestSolution) {
				bestSolution = currentSolution;
				bestSequences = currentSequence;
			}
		}
		return reconstructSequence(bestSequences, jobs);
	}
	
	/**
	 * Generate one subproblem to solve
	 * @param jobs
	 * @param nbrMachines
	 * @return
	 */
	private List<JobDto> generateOneSubProblem(List<JobDto> jobs, Integer nbrMachines){
		List<JobDto> problem = new ArrayList<JobDto>();
		for(JobDto j : jobs) {
			JobDto job = new JobDto();
			job.setId(j.getId());
			Integer m1 = 0, m2 = 0, lastMachine = j.getProcessingTimes().size();
			for(int i=1; i<=nbrMachines; i++) {
				m1 += j.getProcessingTimes().get(i-1);
				m2 += j.getProcessingTimes().get(lastMachine - i);
			}
			job.getProcessingTimes().add(m1);
			job.getProcessingTimes().add(m2);
			problem.add(job);
		}
		return problem;
	}
}
