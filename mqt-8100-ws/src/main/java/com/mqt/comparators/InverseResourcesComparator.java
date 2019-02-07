package com.mqt.comparators;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.mqt.pojo.AbstractResource;

/**
 * Classe de comparaison de deux entities selon le temps inversé
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 19/09/2017
 * @version 1.0
 */
@Component
public class InverseResourcesComparator implements Comparator<AbstractResource> {

  /**
   * Redifinition de la méthode de comparaison
   * 
   * @param e1
   * @param e2
   * @return int
   */
  @Override
  public int compare(AbstractResource e1, AbstractResource e2) {
    if (null != e1 && null != e2 && null != e1.getTimestamps() && null != e2.getTimestamps()) {
      return e2.getTimestamps().compareTo(e1.getTimestamps());
    }
    return 0;
  }

}

