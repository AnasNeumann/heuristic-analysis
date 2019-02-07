package com.mqt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mqt.criteria.InstanceCriteria;
import com.mqt.pojo.SearchResult;
import com.mqt.pojo.entities.InstanceEntity;
import com.mqt.pojo.vo.InstanceVo;
import com.mqt.repositories.InstanceRepository;
import com.mqt.specifications.InstanceSpecification;

/**
 * data access object service
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @verion 1.0
 */
@Service("instanceService")
public class InstanceService extends GenericCrudService<InstanceCriteria, InstanceVo, InstanceEntity> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private InstanceRepository repository;

	@Override
	public InstanceVo getById(Long id) {
		return convert(repository.findOne(id));
	}

	@Modifying
	@Transactional
	@Override
	public Long createOrUpdate(InstanceVo vo) {
		return repository.saveAndFlush(beanMapper.map(vo, InstanceEntity.class)).getId();
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
	public List<InstanceVo> getAll() {
		return convert(repository.findAll());
	}

	@Transactional
	@Override
	public SearchResult<InstanceVo> searchByCriteria(InstanceCriteria criteria, Long startIndex, Long maxResults) {
		Specification<InstanceEntity> spec = InstanceSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<InstanceEntity> page = repository.findAll(spec, paginator);
		SearchResult<InstanceVo> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(convert(page.getContent()));
		return result;
	}

	@Override
	public List<InstanceVo> convert(List<InstanceEntity> entities) {
		return convert(entities, InstanceVo.class);
	}

	@Override
	public InstanceVo convert(InstanceEntity entity) {
		return convert(entity, InstanceVo.class);
	}
}
