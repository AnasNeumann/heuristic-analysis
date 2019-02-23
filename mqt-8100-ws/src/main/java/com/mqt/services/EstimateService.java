package com.mqt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mqt.criteria.EstimateCriteria;
import com.mqt.pojo.SearchResult;
import com.mqt.pojo.entities.EstimateEntity;
import com.mqt.pojo.vo.EstimateVo;
import com.mqt.repositories.EstimateRepository;
import com.mqt.specifications.EstimateSpecification;

/**
 * Data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @verion 1.0
 */
@Service("instanceService")
public class EstimateService extends GenericCrudService<EstimateCriteria, EstimateVo, EstimateEntity> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EstimateRepository repository;

	@Override
	public EstimateVo getById(Long id) {
		return convert(repository.findOne(id));
	}

	@Modifying
	@Transactional
	@Override
	public Long createOrUpdate(EstimateVo vo) {
		return repository.saveAndFlush(beanMapper.map(vo, EstimateEntity.class)).getId();
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
	public List<EstimateVo> getAll() {
		return convert(repository.findAll());
	}

	@Transactional
	@Override
	public SearchResult<EstimateVo> searchByCriteria(EstimateCriteria criteria, Long startIndex, Long maxResults) {
		Specification<EstimateEntity> spec = EstimateSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<EstimateEntity> page = repository.findAll(spec, paginator);
		SearchResult<EstimateVo> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(convert(page.getContent()));
		return result;
	}

	@Override
	public List<EstimateVo> convert(List<EstimateEntity> entities) {
		return convert(entities, EstimateVo.class);
	}

	@Override
	public EstimateVo convert(EstimateEntity entity) {
		return convert(entity, EstimateVo.class);
	}
}
