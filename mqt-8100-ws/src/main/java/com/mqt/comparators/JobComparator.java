package com.mqt.comparators;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.mqt.pojo.dto.flowshop.JobDto;

/**
 * Classe de comparaison de deux entities selon l'id
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
@Component
public class JobComparator implements Comparator<JobDto> {

  /**
   * Redifinition de la méthode de comparaison
   * 
   * @param j1
   * @param j2
   * @return
   */
  @Override
  public int compare(JobDto j1, JobDto j2) {
	return totalProcessingTime(j2).compareTo(totalProcessingTime(j1));
  }

  /**
   * Get the total processing time of a job
   * @param j
   * @return
   */
  private Integer totalProcessingTime(JobDto j) {
	  Integer result = 0;
	  for(Integer p : j.getProcessingTimes()) {
		  result += p;
	  }
	  return result;
  }
}
