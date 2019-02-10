package com.mqt.comparators;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.mqt.pojo.dto.WeightedRankDto;

/**
 * Classe de comparaison de deux rank pondérés
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/02/2019
 * @version 1.0
 */
@Component
public class WeightedRankComparator implements Comparator<WeightedRankDto> {

	  /**
	   * Redifinition de la méthode de comparaison
	   * 
	   * @param e1
	   * @param e2
	   * @return
	   */
	  @Override
	  public int compare(WeightedRankDto e1, WeightedRankDto e2) {
	    if (null != e1 && null != e2 && null != e1.getAbs() && null != e2.getAbs()) {
	      return e1.getAbs().compareTo(e2.getAbs());
	    }
	    return 0;
	  }

	}