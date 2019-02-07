package com.mqt.comparators;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.mqt.pojo.AbstractNumerotableResource;

/**
 * Classe de comparaison de deux entities selon le numero
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
@Component
public class NumerotableResourcesComparator implements Comparator<AbstractNumerotableResource> {

  /**
   * Redifinition de la méthode de comparaison
   * 
   * @param e1
   * @param e2
   * @return
   */
  @Override
  public int compare(AbstractNumerotableResource e1, AbstractNumerotableResource e2) {
    if (null != e1 && null != e2 && null != e1.getPosition() && null != e2.getPosition()) {
      return e1.getPosition().compareTo(e2.getPosition());
    }
    return 0;
  }

}
