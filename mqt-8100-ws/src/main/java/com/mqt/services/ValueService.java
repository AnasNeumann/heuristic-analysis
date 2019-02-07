package com.mqt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mqt.criteria.ValueCriteria;
import com.mqt.pojo.SearchResult;
import com.mqt.pojo.entities.ValueEntity;
import com.mqt.pojo.vo.ValueVo;
import com.mqt.repositories.ValueRepository;
import com.mqt.specifications.ValueSpecification;

/**
 * data access object service
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @verion 1.0
 */
@Service("valueService")
public class ValueService extends GenericCrudService<ValueCriteria, ValueVo, ValueEntity> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ValueRepository repository;

	@Override
	public ValueVo getById(Long id) {
		return convert(repository.findOne(id));
	}

	@Modifying
	@Transactional
	@Override
	public Long createOrUpdate(ValueVo vo) {
		return repository.saveAndFlush(beanMapper.map(vo, ValueEntity.class)).getId();
	}

	@Modifying
	@Transactional
	@Override
	public void delete(Long id) {
		Integer nb = repository.countById(id);
		if (0 < nb) {
			repository.delete(id);
		}
	}

	@Transactional
	@Override
	public List<ValueVo> getAll() {
		return convert(repository.findAll());
	}

	@Transactional
	@Override
	public SearchResult<ValueVo> searchByCriteria(ValueCriteria criteria, Long startIndex, Long maxResults) {
		Specification<ValueEntity> spec = ValueSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<ValueEntity> page = repository.findAll(spec, paginator);
		SearchResult<ValueVo> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(convert(page.getContent()));
		return result;
	}

	@Override
	public List<ValueVo> convert(List<ValueEntity> entities) {
		return convert(entities, ValueVo.class);
	}

	@Override
	public ValueVo convert(ValueEntity entity) {
		return convert(entity, ValueVo.class);
	}
}
