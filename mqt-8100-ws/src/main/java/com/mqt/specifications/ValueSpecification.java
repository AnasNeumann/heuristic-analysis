package com.mqt.specifications;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.mqt.criteria.ValueCriteria;
import com.mqt.pojo.entities.ValueEntity;

/**
 * specification for database research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
public class ValueSpecification {

	/**
	 * private constructor
	 */
	private ValueSpecification() {

	}

	/**
	 * Recherche d'entity par critères.
	 * 
	 * @param criteres
	 * @return Specification
	 */
	public static Specification<ValueEntity> searchByCriteres(final ValueCriteria criteres) {
		return new Specification<ValueEntity>() {
			@Override
			public Predicate toPredicate(Root<ValueEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {

					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long>get("id"), criteres.getId());
						listeCond.add(p);
					}
					
					if (null != criteres.getValue()) {
						Predicate p = cb.equal(root.<Integer>get("value"), criteres.getValue());
						listeCond.add(p);
					}
					
					if (null != criteres.getHeuristicId()) {
						Predicate p = cb.equal(root.<Long>get("heuristicId"), criteres.getHeuristicId());
						listeCond.add(p);
					}

					if (null != criteres.getTimestamps()) {
						Predicate p = cb.equal(root.<Calendar>get("timestamps"), criteres.getTimestamps());
						listeCond.add(p);
					}
				}
				Predicate[] cond = new Predicate[listeCond.size()];
				listeCond.toArray(cond);
				return cb.and(cond);
			}
		};
	}
}
