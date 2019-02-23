package com.mqt.comparators;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.mqt.pojo.vo.EstimateVo;

/**
 * Classe de comparaison de deux estimation selon leur valeurs
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @version 1.0
 */
@Component
public class EstimateComparator implements Comparator<EstimateVo> {

	  /**
	   * Redifinition de la méthode de comparaison
	   * @param e1
	   * @param e2
	   * @return
	   */
	  @Override
	  public int compare(EstimateVo e1, EstimateVo e2) {
	    if (null != e1 && null != e2 && null != e1.getValue() && null != e2.getValue()) {
	      return e1.getValue().compareTo(e2.getValue());
	    }
	    return 0;
	  }

}