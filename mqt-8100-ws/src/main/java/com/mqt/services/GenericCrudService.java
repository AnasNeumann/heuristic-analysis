package com.mqt.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mqt.criteria.Criteria;
import com.mqt.pojo.AbstractResource;
import com.mqt.pojo.SearchResult;

import static java.util.Collections.sort;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * Created by ineumann on 9/17/17.
 */
public abstract class GenericCrudService<C extends Criteria, V extends AbstractResource, E extends AbstractResource>
		extends GenericService {
	private static final long serialVersionUID = 1L;

	/**
	 * Les constantes
	 */
	protected Long PAGINATION = 100L;
	protected Long FIRST_INDEX = 0L;

	public abstract SearchResult<V> searchByCriteria(C criteria, Long startIndex, Long maxResults);

	public abstract V getById(Long id);

	public abstract List<V> getAll();

	public abstract Long createOrUpdate(V vo);

	public abstract void delete(Long id);

	public abstract List<V> convert(List<E> entities);

	public abstract V convert(E entity);

	public <D extends AbstractResource, S extends AbstractResource> List<D> convert(List<S> entities, Class<D> clazz) {
		List<D> vos = (isEmpty(entities)) ? new ArrayList<>()
				: entities.stream().map(e -> convert(e, clazz)).collect(Collectors.toList());
		sort(vos, comparator);
		return vos;
	}

	public <D extends AbstractResource, S extends AbstractResource> D convert(S entity, Class<D> clazz) {
		return (null == entity) ? null : beanMapper.map(entity, clazz);
	}

	public List<V> searchAllByCriteria(C criteria, Boolean inverse) {
		Long startIndex = FIRST_INDEX;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<V> result = null;
		List<V> finalResult = new ArrayList<>();
		do {
			result = this.searchByCriteria(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		sort(finalResult, inverse ? inverseComparator : comparator);
		return finalResult;
	}
}
