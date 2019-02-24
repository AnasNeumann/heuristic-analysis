package com.mqt.engine.estimate;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.IndependenceTestDto;
import com.mqt.pojo.vo.EstimateVo;

/**
 * Module d'estimation : test sur l'indépendance des variables
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/02/2019
 */
@Service("independenceTest")
public class IndependenceTest extends GenericEstimatorEngine {
	
	/**
	 * Generer le résultat blobal du test de l'indépendence
	 * @param estimates
	 * @return
	 */
	public IndependenceTestDto generate(List<EstimateVo> estimates) {
		Collections.sort(estimates, comparatorByValue);
		Double mediane = getMediane(estimates);
		Collections.sort(estimates, comparator);
		IndependenceTestDto result = new IndependenceTestDto()
				.setNbrSequences(getNbrSequence(estimates, mediane))
				.setAverageSequenceNbr((estimates.size()/2.00) + 1.00)
				.setStandardDeviation(getStandardDeviationOfSequences(estimates.size()))
				.setZ(1.96);
		return result.setSuccess(verify(result));
	}
	
	/**
	 * effectuer le test avec les resultats trouvés
	 * @param t
	 * @return
	 */
	private boolean verify(IndependenceTestDto t) {
		return (t.getAverageSequenceNbr() - t.getStandardDeviation()*t.getZ() <= t.getNbrSequences() 
				&& t.getNbrSequences() <= t.getAverageSequenceNbr() + t.getStandardDeviation()*t.getZ());
	}
	
	/**
	 * get the standard deviation of sequence numbers
	 * @param s
	 * @return
	 */
	public Double getStandardDeviationOfSequences(Integer s) {
		return Math.sqrt((s*((s/2.00)-1.00)) / (2.00*(s-1.00)));
	}
	
	/**
	 * Récuperer le nombre de suites d'une liste d'estimation
	 * @param estimates
	 * @return
	 */
	public Integer getNbrSequence(List<EstimateVo> estimates, Double mediane) {
		if(estimates.size() < 2) {
			return 1;
		}
		Integer total = 1;
		Boolean mode = true;
		mode = (estimates.get(0).getValue() <= mediane)? true : false;
		for(EstimateVo e : estimates.subList(1, estimates.size())) {
			if(e.getValue() <= mediane){
				if(mode == false) {
					total++;
					mode = true;
				}
				System.out.println("- : "+e.getValue()+" <= "+mediane);
			} else if( e.getValue() > mediane) {
				if(mode == true) {
					total++;
					mode = false;
				}
				System.out.println("+ : "+e.getValue()+" > "+mediane);
			}
		}
		return total;
	}
	
}
