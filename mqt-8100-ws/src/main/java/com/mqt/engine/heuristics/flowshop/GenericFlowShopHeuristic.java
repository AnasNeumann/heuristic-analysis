package com.mqt.engine.heuristics.flowshop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mqt.comparators.flowshop.ELComparator;
import com.mqt.comparators.flowshop.ERComparator;
import com.mqt.pojo.dto.flowshop.FlowShopInstanceDto;
import com.mqt.pojo.dto.flowshop.JobDto;
import com.mqt.pojo.dto.flowshop.SequenceDto;

/**
 * Module des Heuristiques pour les problèmes de Flow Shop avec permutation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/03/2019
 */
public class GenericFlowShopHeuristic {

	/**
	 * Injection of dependencies
	 */
	@Autowired
	protected ELComparator elComparator;
	@Autowired
	protected ERComparator erComparator;
	
	/**
	 * Parse a file and get the list of all instances
	 * @param file
	 * @return
	 */
	public static List<FlowShopInstanceDto> parse(String f) {
		List<FlowShopInstanceDto> result = new ArrayList<FlowShopInstanceDto>();
		String[] file = f.split(System.lineSeparator());
		Integer nbrOfInstances = new Integer(file[0]);
		int k = 1;
		for(int i=1; i<=nbrOfInstances; i++) {
			FlowShopInstanceDto instance = new FlowShopInstanceDto();
			instance.setId(new Long(file[k]));
			Integer nbrJobs = new Integer(file[k+1]);
			Integer nbrMachines = new Integer(file[k+2]);
			k+=3;
			for(int j=1; j<=nbrJobs; j++) {
				JobDto job = new JobDto();
				job.setId(new Long(j));
				for(int m=1; m<=nbrMachines; m++) {
					job.getProcessingTimes().add(new Integer(file[k]));
					k++;
				}
				instance.getJobs().add(job);
			}
			result.add(instance);
		}
		return result;
	}
	
	/**
	 * Save a 2-machine problem to optimality with johnson
	 * @param jobs
	 * @return
	 */
	protected List<SequenceDto> johnson(List<JobDto> jobs){
		List<SequenceDto> result = new ArrayList<SequenceDto>();
		List<JobDto> EL = new ArrayList<JobDto>();
		List<JobDto> ER = new ArrayList<JobDto>();
		for(JobDto j : jobs) {
			if(j.getProcessingTimes().get(0) <= j.getProcessingTimes().get(1)) {
				EL.add(j);
			}else {
				ER.add(j);
			}
		}
		Collections.sort(EL, elComparator);
		Collections.sort(ER, erComparator);
		for(JobDto j : EL) {
			result.add(new SequenceDto().setJob(j));
		}
		for(JobDto j : ER) {
			result.add(new SequenceDto().setJob(j));
		}
		return result;
	}
	
	/**
	 * Get the enter date of a job in a machine
	 * @param sequence of all currents jobs
	 * @param j the current job index
	 * @param m the current machine index
	 * @return
	 */
	protected Integer getStartTime(List<SequenceDto> sequences, Integer job, Integer machine) {
		if(job == 0) {
			if(machine == 0) {
				return 0;
			} else {
				Integer sumProcessingTimes = 0;
				for(int p=0; p<machine; p++) {
					sumProcessingTimes += sequences.get(0).getJob().getProcessingTimes().get(p);
				}
				return sumProcessingTimes;
			}
		} else {
			Integer endPrecJob = getStartTime(sequences, job-1, machine) + sequences.get(job-1).getJob().getProcessingTimes().get(machine);
			if(machine == 0) {
				return endPrecJob;	
			} else {
				Integer endPrecMachine = getStartTime(sequences, job, machine-1) + sequences.get(job).getJob().getProcessingTimes().get(machine-1);
				return Math.max(endPrecJob, endPrecMachine);
			}
		}
	}
	
	/**
	 * get the makespan of a solution
	 * @param sequences
	 * @param nbrMachines
	 * @return
	 */
	protected Double getMakespan(List<SequenceDto> sequences, Integer nbrMachines) {
		return new Double(getStartTime(sequences, sequences.size()-1, nbrMachines-1) 
				 + sequences.get(sequences.size()-1).getJob().getProcessingTimes().get(nbrMachines-1));
	}
	
	/**
	 * Create a new sequence with the job in a specific position
	 * @param sequences
	 * @param newSequence
	 * @param position
	 * @return
	 */
	protected List<SequenceDto> getNewSequences(List<SequenceDto> sequences, SequenceDto newSequence, Integer position){
		List<SequenceDto> result = new ArrayList<SequenceDto>(sequences);
		result.add(position, newSequence);
		return result;
	}
}
