package com.mqt.engine.heuristics.flowshop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mqt.comparators.flowshop.SlopeIndexComparator;
import com.mqt.pojo.dto.flowshop.FlowShopHeuristicDto;
import com.mqt.pojo.dto.flowshop.FlowShopInstanceDto;
import com.mqt.pojo.dto.flowshop.IndexedJobDto;
import com.mqt.pojo.dto.flowshop.JobDto;
import com.mqt.pojo.dto.flowshop.SequenceDto;

/**
 * Module des Heuristiques pour les problèmes de Flow Shop avec permutation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/03/2019
 */
@Service("palmerHeuristic")
public class PalmerHeuristic extends GenericFlowShopHeuristic {

	/**
	 * Injection of dependencies
	 */
	@Autowired
	private SlopeIndexComparator comparator;
	
	/**
	 * Solve the problem with the Palmer heuristic
	 * @param instance
	 * @return
	 */
	public FlowShopHeuristicDto solve(FlowShopInstanceDto instance) {
		Integer nbrMachines = instance.getJobs().get(0).getProcessingTimes().size();
		List<SequenceDto> sequences = new ArrayList<SequenceDto>();
		for(IndexedJobDto job : getIndexedJobs(instance.getJobs(), nbrMachines)){
			sequences.add(new SequenceDto().setJob(job.getJob()));
		}
		return new FlowShopHeuristicDto().setName("Palmer").setSequences(sequences).setOptimal(getMakespan(sequences, nbrMachines));
	}

	/**
	 * get the indexedJobs
	 * @param jobs
	 * @return
	 */
	private List<IndexedJobDto> getIndexedJobs(List<JobDto> jobs, Integer nbrMachines) {
		List<IndexedJobDto> indexedJobs = new ArrayList<IndexedJobDto>();
		for(JobDto job : jobs) {
			Integer slopeIndex = 0;
			for(int i = 1; i <= nbrMachines; i++) {
				slopeIndex += (nbrMachines - (2*i) + 1) * job.getProcessingTimes().get(i-1);
			}
			indexedJobs.add(new IndexedJobDto().setJob(job).setSlopeIndex(slopeIndex));
		}
		Collections.sort(indexedJobs, comparator);
		return indexedJobs;
	}
}
