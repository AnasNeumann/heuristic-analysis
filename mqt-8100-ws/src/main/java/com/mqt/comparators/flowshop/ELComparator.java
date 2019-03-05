package com.mqt.comparators.flowshop;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.mqt.pojo.dto.flowshop.JobDto;

/**
 * Classe de comparaison selon les processing times
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/03/2019
 * @version 1.0
 */
@Component
public class ELComparator implements Comparator<JobDto> {

	/**
	 * Redifinition de la méthode de comparaison
	 * 
	 * @param j1
	 * @param j2
	 * @return
	 */
	@Override
	public int compare(JobDto j1, JobDto j2) {
		if(null != j1 && null != j2 && null != j1.getProcessingTimes().get(0) &&  null != j2.getProcessingTimes().get(0)) {
			return j1.getProcessingTimes().get(0).compareTo(j2.getProcessingTimes().get(0));
		}
		return 0;
	}

}
