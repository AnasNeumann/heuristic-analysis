package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour une ressource de base
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 17/07/2018
 * @version 1.0
 */
public class AbstractResourceDto extends AbstractResource {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param r
	 */
	public AbstractResourceDto(AbstractResource r) {
		this.id = r.getId();
		this.timestamps = r.getTimestamps();
	}
	
}