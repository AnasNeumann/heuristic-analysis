package com.mqt.specifications;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.mqt.criteria.ProfileCriteria;
import com.mqt.pojo.entities.ProfileEntity;

/**
 * specification for database research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
public class ProfileSpecification {

	/**
	 * private constructor
	 */
	private ProfileSpecification() {

	}

	/**
	 * Recherche d'entity par critères.
	 * 
	 * @param criteres
	 * @return Specification
	 */
	public static Specification<ProfileEntity> searchByCriteres(final ProfileCriteria criteres) {
		return new Specification<ProfileEntity>() {
			@Override
			public Predicate toPredicate(Root<ProfileEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {

					if (null != criteres.getLastName()) {
						Predicate p = cb.like(cb.lower(root.<String>get("lastName")),
								criteres.getLastName().toLowerCase() + "%");
						listeCond.add(p);
					}

					if (null != criteres.getFirstName()) {
						Predicate p = cb.like(cb.lower(root.<String>get("firstName")),
								criteres.getFirstName().toLowerCase() + "%");
						listeCond.add(p);
					}

					if (null != criteres.getJob()) {
						Predicate p = cb.like(cb.lower(root.<String>get("job")),
								criteres.getJob().toLowerCase() + "%");
						listeCond.add(p);
					}

					if (null != criteres.getPhoneNumber()) {
						Predicate p = cb.like(cb.lower(root.<String>get("phoneNumber")),
								criteres.getPhoneNumber().toLowerCase() + "%");
						listeCond.add(p);
					}

					if (null != criteres.getCategory()) {
						Predicate p = cb.like(cb.lower(root.<String>get("category")),
								criteres.getCategory().toLowerCase() + "%");
						listeCond.add(p);
					}

					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long>get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getBornDate()) {
						Predicate p = cb.equal(root.<Calendar>get("bornDate"), criteres.getBornDate());
						listeCond.add(p);
					}
					
					if (null != criteres.getIsVisible()) {
						Predicate p = cb.equal(root.<Boolean>get("isVisible"), criteres.getIsVisible());
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
