package com.mqt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mqt.criteria.HeuristicCriteria;
import com.mqt.pojo.SearchResult;
import com.mqt.pojo.entities.HeuristicEntity;
import com.mqt.pojo.vo.HeuristicVo;
import com.mqt.repositories.HeuristicRepository;
import com.mqt.specifications.HeuristicSpecification;

/**
 * data access object service
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @verion 1.0
 */
@Service("heuristicService")
public class HeuristicService extends GenericCrudService<HeuristicCriteria, HeuristicVo, HeuristicEntity> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private HeuristicRepository repository;

	@Override
	public HeuristicVo getById(Long id) {
		return convert(repository.findOne(id));
	}

	@Modifying
	@Transactional
	@Override
	public Long createOrUpdate(HeuristicVo vo) {
		return repository.saveAndFlush(beanMapper.map(vo, HeuristicEntity.class)).getId();
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
	public List<HeuristicVo> getAll() {
		return convert(repository.findAll());
	}

	@Transactional
	@Override
	public SearchResult<HeuristicVo> searchByCriteria(HeuristicCriteria criteria, Long startIndex, Long maxResults) {
		Specification<HeuristicEntity> spec = HeuristicSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<HeuristicEntity> page = repository.findAll(spec, paginator);
		SearchResult<HeuristicVo> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(convert(page.getContent()));
		return result;
	}

	@Override
	public List<HeuristicVo> convert(List<HeuristicEntity> entities) {
		return convert(entities, HeuristicVo.class);
	}

	@Override
	public HeuristicVo convert(HeuristicEntity entity) {
		return convert(entity, HeuristicVo.class);
	}
}
