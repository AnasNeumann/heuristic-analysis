package com.mqt.pojo.dto;

import java.util.ArrayList;
import java.util.List;

import com.mqt.pojo.AbstractResource;
import com.mqt.pojo.vo.HeuristicVo;

/**
 * DTO pour l'analyse de la dominance stochastique empirique
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2019
 * @version 1.0
 */
public class DominanceDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private HeuristicVo h;
	private List<InstancesByDeviationDto> values = new ArrayList<InstancesByDeviationDto>();

	/**
	 * @return the h
	 */
	public HeuristicVo getH() {
		return h;
	}

	/**
	 * @param h
	 *            the h to set
	 */
	public DominanceDto setH(HeuristicVo h) {
		this.h = h;
		return this;
	}

	/**
	 * @return the values
	 */
	public List<InstancesByDeviationDto> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public DominanceDto setValues(List<InstancesByDeviationDto> values) {
		this.values = values;
		return this;
	}

}
