package com.mqt.comparators;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.mqt.pojo.AbstractResource;

/**
 * Classe de comparaison de deux entities selon le temps
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
@Component
public class ResourcesComparator implements Comparator<AbstractResource> {

  /**
   * Redifinition de la méthode de comparaison
   * 
   * @param e1
   * @param e2
   * @return
   */
  @Override
  public int compare(AbstractResource e1, AbstractResource e2) {
    if (null != e1 && null != e2 && null != e1.getTimestamps() && null != e2.getTimestamps()) {
      return e1.getTimestamps().compareTo(e2.getTimestamps());
    }
    return 0;
  }

}
