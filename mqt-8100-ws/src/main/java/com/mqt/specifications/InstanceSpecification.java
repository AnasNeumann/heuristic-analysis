package com.mqt.specifications;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.mqt.criteria.InstanceCriteria;
import com.mqt.pojo.entities.InstanceEntity;

/**
 * specification for database research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
public class InstanceSpecification {

	/**
	 * private constructor
	 */
	private InstanceSpecification() {

	}

	/**
	 * Recherche d'entity par critères.
	 * 
	 * @param criteres
	 * @return Specification
	 */
	public static Specification<InstanceEntity> searchByCriteres(final InstanceCriteria criteres) {
		return new Specification<InstanceEntity>() {
			@Override
			public Predicate toPredicate(Root<InstanceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {

					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long>get("id"), criteres.getId());
						listeCond.add(p);
					}
					
					if (null != criteres.getOptimal()) {
						Predicate p = cb.equal(root.<Integer>get("optimal"), criteres.getOptimal());
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
