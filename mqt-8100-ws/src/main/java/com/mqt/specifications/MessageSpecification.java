package com.mqt.specifications;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.mqt.criteria.MessageCriteria;
import com.mqt.pojo.entities.MessageEntity;

/**
 * specification for database research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
public class MessageSpecification {

  /**
   * private constructor
   */
  private MessageSpecification() {

  }

	/**
	 * Recherche d'entity par critères.
	 * 
	 * @param criteres
	 * @return Specification
	 */
	public static Specification<MessageEntity> searchByCriteres(final MessageCriteria criteres) {
		return new Specification<MessageEntity>() {
			@Override
			public Predicate toPredicate(Root<MessageEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {

					if (null != criteres.getMail()) {
						Predicate p = cb.like(cb.lower(root.<String>get("mail")),
								criteres.getMail().toLowerCase() + "%");
						listeCond.add(p);
					}

					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long>get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getState()) {
						Predicate p = cb.equal(root.<Integer>get("state"), criteres.getState());
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
