package com.mqt.engine.analyze;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.SingleAverageStudentTestDto;
import com.mqt.pojo.vo.HeuristicVo;
import com.mqt.pojo.vo.ValueVo;

/**
 * Module d'analyse : test des moyennes de student
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/02/2019
 */
@Service("studentTestAnalyzer")
public class StudentTestAnalyzer extends GenericAnalyzer {

	/**
	 * Constantes 
	 */
	private Map<Integer,Double> CRITICAL_VALUES = new HashMap<Integer,Double>();
	private final String AVERAGE = "average";
	private final String STANDARD = "standard";

    /**
     * Comparer deux heuristiques selon le test des moyennes de students a=5% 
     * @param h1
     * @param h2
     * @param n
     * @return
     */
	public SingleAverageStudentTestDto analyze(HeuristicVo h1, HeuristicVo h2, Integer n) {
		Map<String, Double> vH1 = getAverageAndStandardVariance(h1);
		Map<String, Double> vH2 = getAverageAndStandardVariance(h2);
		Double value = (vH1.get(AVERAGE) - vH2.get(AVERAGE))/Math.sqrt((Math.pow(vH1.get(STANDARD),2) + Math.pow(vH1.get(STANDARD),2) / n));
		return new SingleAverageStudentTestDto().setName(h2.getName()).setValue(value).setH0(value >= getT(n));
	}
	
	/**
	 * Compute the average deviance and the standard deviance of an heuristic
	 * @param h
	 * @return
	 */
	public Map<String, Double> getAverageAndStandardVariance(HeuristicVo h){
		Map<String, Double> result = new HashMap<String,Double>();
		Double totalDeviation = 0.0;
		List<Integer> deviations = new ArrayList<Integer>();
		for(ValueVo v : h.getValues()) {
			totalDeviation += v.getValue() - v.getInstance().getOptimal();
			deviations.add(v.getValue() - v.getInstance().getOptimal());
		}
		result.put(AVERAGE, totalDeviation / h.getValues().size());
		result.put(STANDARD, standardDeviation(deviations, result.get(AVERAGE)));
		return result;
	}
	
	/**
	 * Get the actual value of T(a, 2n-2)
	 * @param n
	 * @return
	 */
	public Double getT(Integer n) {
		return this.criticalValue().get((2*n)-2);
	}
	
   /**
    * Récuperer la table des valeurs critiques de students pour a=5% 
    * @return CRITICAL_VALUES
    */
   public Map<Integer,Double> criticalValue(){
	   if(!CRITICAL_VALUES.isEmpty()) {
		   return CRITICAL_VALUES;
	   }
	   CRITICAL_VALUES.put(1, 12.706);
	   CRITICAL_VALUES.put(2, 4.303);
	   CRITICAL_VALUES.put(3, 3.182);
	   CRITICAL_VALUES.put(4, 2.776);
	   CRITICAL_VALUES.put(5, 2.571);
	   CRITICAL_VALUES.put(6, 2.447);
	   CRITICAL_VALUES.put(7, 2.365);
	   CRITICAL_VALUES.put(8, 2.262);
	   CRITICAL_VALUES.put(9, 2.225);
	   CRITICAL_VALUES.put(10, 2.201);
	   CRITICAL_VALUES.put(11, 2.201);
	   CRITICAL_VALUES.put(12, 2.179);
	   CRITICAL_VALUES.put(13, 2.160);
	   CRITICAL_VALUES.put(14, 2.145);
	   CRITICAL_VALUES.put(15, 2.131);
	   CRITICAL_VALUES.put(16, 2.120);
	   CRITICAL_VALUES.put(17, 2.110);
	   CRITICAL_VALUES.put(18, 2.101);
	   CRITICAL_VALUES.put(19, 2.093);
	   CRITICAL_VALUES.put(20, 2.086);
	   CRITICAL_VALUES.put(21, 2.080);
	   CRITICAL_VALUES.put(22, 2.074);
	   CRITICAL_VALUES.put(23, 2.069);
	   CRITICAL_VALUES.put(24, 2.064);
	   CRITICAL_VALUES.put(25, 2.060);
	   CRITICAL_VALUES.put(26, 2.056);
	   CRITICAL_VALUES.put(27, 2.052);
	   CRITICAL_VALUES.put(28, 2.048);
	   CRITICAL_VALUES.put(29, 2.045);
	   CRITICAL_VALUES.put(30, 2.042);
	   CRITICAL_VALUES.put(40, 2.021);
	   CRITICAL_VALUES.put(50, 2.009);
	   CRITICAL_VALUES.put(60, 2.000);
	   CRITICAL_VALUES.put(80, 1.990);
	   CRITICAL_VALUES.put(120, 1.984);
	   return CRITICAL_VALUES;
   }
}
