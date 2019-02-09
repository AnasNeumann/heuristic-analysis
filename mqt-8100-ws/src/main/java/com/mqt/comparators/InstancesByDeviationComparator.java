package com.mqt.comparators;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.mqt.pojo.dto.InstancesByDeviationDto;

/**
 * Classe de comparaison pour la dominance stochastique
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2019
 * @version 1.0
 */
@Component
public class InstancesByDeviationComparator implements Comparator<InstancesByDeviationDto> {

  /**
   * Redifinition de la méthode de comparaison
   * 
   * @param e1
   * @param e2
   * @return
   */
  @Override
  public int compare(InstancesByDeviationDto e1, InstancesByDeviationDto e2) {
    if (null != e1 && null != e2 && null != e1.getDeviation() && null != e2.getDeviation()) {
      return e1.getDeviation().compareTo(e2.getDeviation());
    }
    return 0;
  }

}

