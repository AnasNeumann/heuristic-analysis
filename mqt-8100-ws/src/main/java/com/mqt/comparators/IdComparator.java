package com.mqt.comparators;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.mqt.pojo.AbstractResource;

/**
 * Classe de comparaison de deux entities selon l'id
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
@Component
public class IdComparator implements Comparator<AbstractResource> {

  /**
   * Redifinition de la méthode de comparaison
   * 
   * @param e1
   * @param e2
   * @return
   */
  @Override
  public int compare(AbstractResource e1, AbstractResource e2) {
    if (null != e1 && null != e2 && null != e1.getId() && null != e2.getId()) {
      return e1.getId().compareTo(e2.getId());
    }
    return 0;
  }

}
