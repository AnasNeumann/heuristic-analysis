package com.mqt.comparators;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.mqt.pojo.dto.flowshop.IndexedJobDto;

/**
 * Classe de comparaison de deux entities selon l'id
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
@Component
public class SlopeIndexComparator implements Comparator<IndexedJobDto> {

  /**
   * Redifinition de la méthode de comparaison
   * 
   * @param j1
   * @param j2
   * @return
   */
  @Override
  public int compare(IndexedJobDto j1, IndexedJobDto j2) {
	if(null != j1 && null != j2 && null != j1.getSlopeIndex() &&  null != j2.getSlopeIndex()) {
		return j1.getSlopeIndex().compareTo(j2.getSlopeIndex());
	}
	return 0;
  }

}
