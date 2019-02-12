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
public class EvaluationCriteriaAnalyzer extends GenericAnalyzer{
	
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
		Double maxDeviation = 0.0;
		Double averageDeviation = 0.0;
		List<Double> deviations = new ArrayList<Double>();
		for(ValueVo v : h.getValues()) {
			boolean isWorst = true;
			Integer nbrOptimal = 0;
			double d = getDeviation(v);
			deviations.add(d);
			averageDeviation += d;
			if(d > maxDeviation) {
				maxDeviation = d;
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
		averageDeviation = averageDeviation / h.getValues().size();
		return c.setMaxDeviation(maxDeviation)
				.setAverageDeviation(averageDeviation)
				.setStandardDeviation(standardDeviation(deviations, averageDeviation))
				.setH(h.setValues(null));
	}
}
