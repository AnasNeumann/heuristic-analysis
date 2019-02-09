package com.mqt.engine.analyze;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.CriteriaDto;
import com.mqt.pojo.vo.HeuristicVo;
import com.mqt.pojo.vo.ValueVo;
import com.mqt.services.ValueService;

/**
 * Module d'analyse : critères d'évaluation 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2019
 */
@Service("evaluationCriteriaAnalyzer")
public class EvaluationCriteriaAnalyzer {
	
	/**
	 * Injection of dependencies
	 */
	@Autowired
	private ValueService vService;

	/**
	 * Résultat de l'analyse complète des critères pour une heuristique
	 * @param h
	 * @param max
	 * @return
	 */
	public CriteriaDto analyze(HeuristicVo h, Integer max) {
		CriteriaDto c = new CriteriaDto();
		Integer maxDeviation = 0;
		Float averageDeviation = 0.0F;
		List<Integer> deviations = new ArrayList<Integer>();
		for(ValueVo v : h.getValues()) {
			boolean isWorst = true;
			Integer nbrOptimal = 0;
			deviations.add(v.getValue() - v.getInstance().getOptimal());
			averageDeviation += v.getValue() - v.getInstance().getOptimal();
			if(v.getValue() - v.getInstance().getOptimal() > maxDeviation) {
				maxDeviation = v.getValue() - v.getInstance().getOptimal();
			}
			for(ValueVo value : vService.searchByInstanceId(v.getInstance().getId(), max)){
				if(value.getValue().equals(v.getInstance().getOptimal())){
					if(!value.getHeuristicId().equals(h.getId())) {
						nbrOptimal++;
					} else {
						nbrOptimal-=1;
						c.setNbrOptimal(c.getNbrOptimal() + 1);
					}	
				} else if(value.getValue() > v.getValue()) {
					isWorst = false;
				}
			}
			c.setNbrWorstValue(c.getNbrWorstValue() + (isWorst? 1 : 0));
			c.setOptimalAlone(c.getOptimalAlone() + (0 > nbrOptimal? 1 : 0));
		}
		return c.setMaxDeviation(maxDeviation)
				.setAverageDeviation(averageDeviation/h.getValues().size())
				.setStandardDeviation(standardDeviation(deviations, averageDeviation))
				.setH(h.setValues(null));
	}

	/**
	 * Calculer l'écart type des déviations
	 * @param deviations
	 * @param averageDeviation
	 * @return
	 */
	private Double standardDeviation(List<Integer> deviations, Float averageDeviation) {
		Double sd = 0.0;
		for(Integer d : deviations) {
			sd += Math.pow(d - averageDeviation, 2);
		}
		return Math.sqrt(sd/deviations.size());	
	}
	
}
