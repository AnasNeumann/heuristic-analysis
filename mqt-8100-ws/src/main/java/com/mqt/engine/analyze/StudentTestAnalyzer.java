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
	private Map<Integer,Double> CRITICAL_VALUES_5 = new HashMap<Integer,Double>();
	private Map<Integer,Double> CRITICAL_VALUES_50 = new HashMap<Integer,Double>();
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
		return new SingleAverageStudentTestDto().setName(h2.getName()).setValue(value).setT20(getT(n, 20)).setT5(getT(n, 5));
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
	public Double getT(Integer n, Integer a) {
		return this.criticalValue(a).get((2*n)-2);
	}
	
   /**
    * Récuperer la table des valeurs critiques de students pour a=5% 
    * @return CRITICAL_VALUES_5
    */
   public Map<Integer,Double> criticalValue(Integer a){
	   if(a.equals(5)) {
		   if(!CRITICAL_VALUES_5.isEmpty()) {
			   return CRITICAL_VALUES_5;
		   }
		   CRITICAL_VALUES_5.put(1, 12.706);
		   CRITICAL_VALUES_5.put(2, 4.303);
		   CRITICAL_VALUES_5.put(3, 3.182);
		   CRITICAL_VALUES_5.put(4, 2.776);
		   CRITICAL_VALUES_5.put(5, 2.571);
		   CRITICAL_VALUES_5.put(6, 2.447);
		   CRITICAL_VALUES_5.put(7, 2.365);
		   CRITICAL_VALUES_5.put(8, 2.262);
		   CRITICAL_VALUES_5.put(9, 2.225);
		   CRITICAL_VALUES_5.put(10, 2.201);
		   CRITICAL_VALUES_5.put(11, 2.201);
		   CRITICAL_VALUES_5.put(12, 2.179);
		   CRITICAL_VALUES_5.put(13, 2.160);
		   CRITICAL_VALUES_5.put(14, 2.145);
		   CRITICAL_VALUES_5.put(15, 2.131);
		   CRITICAL_VALUES_5.put(16, 2.120);
		   CRITICAL_VALUES_5.put(17, 2.110);
		   CRITICAL_VALUES_5.put(18, 2.101);
		   CRITICAL_VALUES_5.put(19, 2.093);
		   CRITICAL_VALUES_5.put(20, 2.086);
		   CRITICAL_VALUES_5.put(21, 2.080);
		   CRITICAL_VALUES_5.put(22, 2.074);
		   CRITICAL_VALUES_5.put(23, 2.069);
		   CRITICAL_VALUES_5.put(24, 2.064);
		   CRITICAL_VALUES_5.put(25, 2.060);
		   CRITICAL_VALUES_5.put(26, 2.056);
		   CRITICAL_VALUES_5.put(27, 2.052);
		   CRITICAL_VALUES_5.put(28, 2.048);
		   CRITICAL_VALUES_5.put(29, 2.045);
		   CRITICAL_VALUES_5.put(30, 2.042);
		   CRITICAL_VALUES_5.put(40, 2.021);
		   CRITICAL_VALUES_5.put(50, 2.009);
		   CRITICAL_VALUES_5.put(60, 2.000);
		   CRITICAL_VALUES_5.put(80, 1.990);
		   CRITICAL_VALUES_5.put(100, 1.984);
		   return CRITICAL_VALUES_5; 
	   }else {
		   if(!CRITICAL_VALUES_50.isEmpty()) {
			   return CRITICAL_VALUES_50;
		   }
		   CRITICAL_VALUES_50.put(1, 1.000);
		   CRITICAL_VALUES_50.put(2, 0.16);
		   CRITICAL_VALUES_50.put(3, 0.765);
		   CRITICAL_VALUES_50.put(4, 0.741);
		   CRITICAL_VALUES_50.put(5, 0.727);
		   CRITICAL_VALUES_50.put(6, 0.718);
		   CRITICAL_VALUES_50.put(7, 0.711);
		   CRITICAL_VALUES_50.put(8, 0.706);
		   CRITICAL_VALUES_50.put(9, 0.703);
		   CRITICAL_VALUES_50.put(10, 0.700);
		   CRITICAL_VALUES_50.put(11, 0.697);
		   CRITICAL_VALUES_50.put(12, 0.695);
		   CRITICAL_VALUES_50.put(13, 0.694);
		   CRITICAL_VALUES_50.put(14, 0.692);
		   CRITICAL_VALUES_50.put(15, 0.691);
		   CRITICAL_VALUES_50.put(16, 0.690);
		   CRITICAL_VALUES_50.put(17, 0.689);
		   CRITICAL_VALUES_50.put(18, 0.688);
		   CRITICAL_VALUES_50.put(19, 0.688);
		   CRITICAL_VALUES_50.put(20, 0.687);
		   CRITICAL_VALUES_50.put(21, 0.686);
		   CRITICAL_VALUES_50.put(22, 0.686);
		   CRITICAL_VALUES_50.put(23, 0.685);
		   CRITICAL_VALUES_50.put(24, 0.685);
		   CRITICAL_VALUES_50.put(25, 0.684);
		   CRITICAL_VALUES_50.put(26, 0.684);
		   CRITICAL_VALUES_50.put(27, 0.684);
		   CRITICAL_VALUES_50.put(28, 0.683);
		   CRITICAL_VALUES_50.put(29, 0.683);
		   CRITICAL_VALUES_50.put(30, 0.683);
		   CRITICAL_VALUES_50.put(40, 0.681);
		   CRITICAL_VALUES_50.put(50, 0.679);
		   CRITICAL_VALUES_50.put(60, 0.679);
		   CRITICAL_VALUES_50.put(80, 0.678);
		   CRITICAL_VALUES_50.put(100, 0.677);
		   return CRITICAL_VALUES_50;
	   }
	   
   }
}
