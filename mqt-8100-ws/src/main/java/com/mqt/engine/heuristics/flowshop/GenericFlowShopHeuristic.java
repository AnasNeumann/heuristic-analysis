package com.mqt.engine.heuristics.flowshop;

import java.util.ArrayList;
import java.util.List;

import com.mqt.pojo.dto.flowshop.FlowShopInstanceDto;
import com.mqt.pojo.dto.flowshop.JobDto;

/**
 * Module des Heuristiques pour les problèmes de Flow Shop avec permutation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/03/2019
 */
public class GenericFlowShopHeuristic {

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
}
