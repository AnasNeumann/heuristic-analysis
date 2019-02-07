package com.mqt.pojo;

/**
 * Mother class for all numerotable entities
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 25/08/2017
 */
public abstract class AbstractNumerotableResource extends AbstractResource {
	private static final long serialVersionUID = 1L;
	protected Integer position;

  /**
   * @return the position
   */
  public Integer getPosition() {
    return position;
  }

  /**
   * @param position the position to set
   */
  public AbstractNumerotableResource setPosition(Integer position) {
    this.position = position;
    return this;
  }
}
