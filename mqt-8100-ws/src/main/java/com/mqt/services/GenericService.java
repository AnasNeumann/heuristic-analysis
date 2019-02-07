package com.mqt.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.mqt.beans.BeanMapper;
import com.mqt.comparators.InverseResourcesComparator;
import com.mqt.comparators.NumerotableResourcesComparator;
import com.mqt.comparators.ResourcesComparator;
import com.mqt.pojo.SearchResult;

import java.io.Serializable;

/**
 * generic mother class for data access object service
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
public abstract class GenericService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	protected BeanMapper beanMapper;

	@Autowired
	protected NumerotableResourcesComparator numerotableComparator;

	@Autowired
	protected ResourcesComparator comparator;

	@Autowired
	protected InverseResourcesComparator inverseComparator;

	/**
	 * SearchReasult Object Init
	 * 
	 * @param startIndex
	 * @param maxResults
	 * @return SearchResult<T>
	 */
	public static <T> SearchResult<T> initSearchResult(Long startIndex, Long maxResults) {
		return new SearchResult<T>().setTotalResults(0L).setStartIndex(startIndex).setMaxResults(maxResults);
	}

}
