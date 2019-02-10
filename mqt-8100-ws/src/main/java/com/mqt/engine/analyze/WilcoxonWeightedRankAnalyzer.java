package com.mqt.engine.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mqt.comparators.WeightedRankComparator;
import com.mqt.pojo.dto.SingleWilcoxonTestDto;
import com.mqt.pojo.dto.WeightedRankDto;
import com.mqt.pojo.vo.HeuristicVo;

/**
 * Module d'analyse : test des rangs pondérés de Wilcoxon
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/02/2019
 */
@Service("wilcoxonWeightedRankAnalyzer")
public class WilcoxonWeightedRankAnalyzer extends GenericAnalyzer {
	
	/**
	 * Injection de dépendances
	 */
	@Autowired
	private WeightedRankComparator comparator;
	
	/**
	 * Constantes 
	 */
	private Map<Integer,Map<Integer,Integer>> CRITICAL_VALUES = new HashMap<Integer,Map<Integer,Integer>>();
	private final Integer PERCENTAGE_95 = 95;
	private final Integer PERCENTAGE_99 = 99;
	
	/**
	 * Analyse des rangs pondérés de Wilcoxon
	 * @param h1
	 * @param h2
	 * @param n
	 * @return
	 */
	public SingleWilcoxonTestDto analyze(HeuristicVo h1, HeuristicVo h2, Integer n) {
		SingleWilcoxonTestDto result = new SingleWilcoxonTestDto().setName(h2.getName());
		Integer sum = 0;
		int rank = 1;
		for(WeightedRankDto r : getWeightedRank(h1, h2, n)) {
			sum += r.getSign() * rank;
			rank++;
		}
		return result.setValue(sum).setW95(criticalValue().get(PERCENTAGE_95).get(n)).setW99(criticalValue().get(PERCENTAGE_99).get(n));
	}
	
	/**
	 * Get the list of weighted rank
	 * @param h1
	 * @param h2
	 * @return
	 */
	public List<WeightedRankDto> getWeightedRank(HeuristicVo h1, HeuristicVo h2, Integer n){
		List<WeightedRankDto> result = new ArrayList<WeightedRankDto>();
		for(int i=0; i<n; i++) {
			int d1 = getDeviation(h1.getValues().get(i));
			int d2 = getDeviation(h2.getValues().get(i));
			int sign = (d1 - d2 > 0)? 1 : -1;
			result.add(new WeightedRankDto().setAbs(Math.abs(d1 - d2)).setSign(sign));
		}
		Collections.sort(result, comparator);
		return result;
	}

	/**
	 * Récuperer le tableau de valeurs rempli
	 * @return
	 */
	public Map<Integer,Map<Integer,Integer>> criticalValue(){
	   if(!CRITICAL_VALUES.isEmpty()) {
		   return CRITICAL_VALUES;
	   }   
	   Map<Integer,Integer> VALUES_PERCENTAGE_95 = new HashMap<Integer,Integer>();
	   VALUES_PERCENTAGE_95.put(10, 32);
	   VALUES_PERCENTAGE_95.put(11, 37);
	   VALUES_PERCENTAGE_95.put(12, 42);
	   VALUES_PERCENTAGE_95.put(13, 47);
	   VALUES_PERCENTAGE_95.put(14, 52);
	   VALUES_PERCENTAGE_95.put(15, 58);
	   VALUES_PERCENTAGE_95.put(16, 64);
	   VALUES_PERCENTAGE_95.put(17, 70);
	   VALUES_PERCENTAGE_95.put(18, 76);
	   VALUES_PERCENTAGE_95.put(19, 82);
	   VALUES_PERCENTAGE_95.put(20, 88);
	   VALUES_PERCENTAGE_95.put(21, 95);
	   VALUES_PERCENTAGE_95.put(22, 101);
	   VALUES_PERCENTAGE_95.put(23, 108);
	   VALUES_PERCENTAGE_95.put(24, 115);
	   VALUES_PERCENTAGE_95.put(25, 122);
	   VALUES_PERCENTAGE_95.put(26, 130);
	   VALUES_PERCENTAGE_95.put(27, 137);
	   VALUES_PERCENTAGE_95.put(28, 144);
	   VALUES_PERCENTAGE_95.put(29, 152);
	   VALUES_PERCENTAGE_95.put(30, 160);
	   CRITICAL_VALUES.put(PERCENTAGE_95, VALUES_PERCENTAGE_95);
	   
	   Map<Integer,Integer> VALUES_PERCENTAGE_99 = new HashMap<Integer,Integer>();
	   VALUES_PERCENTAGE_99.put(10, 46);
	   VALUES_PERCENTAGE_99.put(11, 52);
	   VALUES_PERCENTAGE_99.put(12, 59);
	   VALUES_PERCENTAGE_99.put(13, 67);
	   VALUES_PERCENTAGE_99.put(14, 74);
	   VALUES_PERCENTAGE_99.put(15, 82);
	   VALUES_PERCENTAGE_99.put(16, 90);
	   VALUES_PERCENTAGE_99.put(17, 98);
	   VALUES_PERCENTAGE_99.put(18, 107);
	   VALUES_PERCENTAGE_99.put(19, 116);
	   VALUES_PERCENTAGE_99.put(20, 125);
	   VALUES_PERCENTAGE_99.put(21, 134);
	   VALUES_PERCENTAGE_99.put(22, 143);
	   VALUES_PERCENTAGE_99.put(23, 153);
	   VALUES_PERCENTAGE_99.put(24, 163);
	   VALUES_PERCENTAGE_99.put(25, 173);
	   VALUES_PERCENTAGE_99.put(26, 183);
	   VALUES_PERCENTAGE_99.put(27, 194);
	   VALUES_PERCENTAGE_99.put(28, 204);
	   VALUES_PERCENTAGE_99.put(29, 215);
	   VALUES_PERCENTAGE_99.put(30, 226);
	   CRITICAL_VALUES.put(PERCENTAGE_99, VALUES_PERCENTAGE_99);
	   return CRITICAL_VALUES;
	}
}
