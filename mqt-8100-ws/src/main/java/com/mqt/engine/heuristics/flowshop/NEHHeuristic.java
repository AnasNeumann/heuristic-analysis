package com.mqt.engine.heuristics.flowshop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mqt.comparators.flowshop.JobComparator;
import com.mqt.pojo.dto.flowshop.FlowShopHeuristicDto;
import com.mqt.pojo.dto.flowshop.FlowShopInstanceDto;
import com.mqt.pojo.dto.flowshop.JobDto;
import com.mqt.pojo.dto.flowshop.SequenceDto;

/**
 * Module des Heuristiques pour les problèmes de Flow Shop avec permutation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/03/2019
 */
@Service("nehHeuristic")
public class NEHHeuristic extends GenericFlowShopHeuristic {

	/**
	 * Injection of denpendencies
	 */
	@Autowired
	private JobComparator comparator;
	
	/**
	 * solve one instance by NEH
	 * @param jobs
	 * @return
	 */
	public FlowShopHeuristicDto solve(FlowShopInstanceDto instance) {
		List<JobDto> jobs = new ArrayList<JobDto>(instance.getJobs());
		List<SequenceDto> sequences = new ArrayList<SequenceDto>();
		Integer nbrMachines = jobs.get(0).getProcessingTimes().size();
		Collections.sort(jobs, comparator);
		Double makespanMin = null;
		for(JobDto job : jobs) {
			makespanMin = null;
			Integer choixPosition = 0;
			SequenceDto newSequence = new SequenceDto().setJob(job);
			for(int i=0; i<=sequences.size(); i++) {
				 Double newMakeSpan =  getMakespan(getNewSequences(sequences, newSequence, i), nbrMachines);
				 if(null == makespanMin || makespanMin > newMakeSpan) {
					 makespanMin = newMakeSpan;
					 choixPosition = i;
				 }
			}
			sequences = getNewSequences(sequences, newSequence, choixPosition);
		}
		return new FlowShopHeuristicDto().setName("NEH : Nawaz, Enscore & Ham").setSequences(sequences).setOptimal(makespanMin);
	}
}
