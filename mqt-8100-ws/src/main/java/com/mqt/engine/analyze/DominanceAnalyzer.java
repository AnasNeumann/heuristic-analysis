package com.mqt.engine.analyze;

import java.util.List;
import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.DominanceDto;
import com.mqt.pojo.dto.InstancesByDeviationDto;
import com.mqt.pojo.vo.HeuristicVo;
import com.mqt.pojo.vo.ValueVo;

/**
 * Module d'analyse : dominance empirique et stochastique
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2019
 */
@Service("dominanceAnalyzer")
public class DominanceAnalyzer {

	/**
	 * Résultat de l'analyse pour une heuristique
	 * @param h
	 * @return
	 */
	public DominanceDto analyze(HeuristicVo h, Integer total) {
		DominanceDto d = new DominanceDto();
		for(ValueVo v : h.getValues()) {
			Integer deviation = getPourcentageByNumber(v.getValue() - v.getInstance().getOptimal(), v.getInstance().getOptimal());
			addAtPlace(deviation ,d.getValues());
		}
		return d.setH(h.setValues(null)).setValues(changePercentage(d.getValues(), total));
	}

	/**
	 * Ajouter une nouvelle déviation dans la liste
	 * @param d
	 * @param values
	 */
	public void addAtPlace(Integer d, List<InstancesByDeviationDto> values) {
		boolean isPresent = false;
		for(InstancesByDeviationDto v : values) {
			if(v.getDeviation() >= d) {
				v.setInstances(v.getInstances() + 1);
				if(v.getDeviation().equals(d)) {
					isPresent = true;
				}
			}
		}
		if(!isPresent) {
			values.add(new InstancesByDeviationDto().setDeviation(d).setInstances(1));
		}
	}
	
	/**
	 * Change number of instance by percentage
	 * @param values
	 * @return
	 */
	public List<InstancesByDeviationDto> changePercentage(List<InstancesByDeviationDto> values, Integer total){
		for(InstancesByDeviationDto v : values) {
			v.setInstances(getPourcentageByNumber(v.getInstances(), total));
		}
		return values;
	}

	/**
	 * Get the pourcentage of instance or deviation
	 * @param nbr
	 * @param total
	 * @return
	 */
	private Integer getPourcentageByNumber(Integer nbr, Integer total) {
		return (nbr*100)/total;
	}
}
