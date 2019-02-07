package com.mqt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mqt.criteria.ProfileCriteria;
import com.mqt.pojo.SearchResult;
import com.mqt.pojo.entities.ProfileEntity;
import com.mqt.pojo.vo.ProfileVo;
import com.mqt.repositories.ProfileRepository;
import com.mqt.specifications.ProfileSpecification;

/**
 * data access object service
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2107
 * @verion 1.0
 */
@Service("profileService")
public class ProfileService extends GenericCrudService<ProfileCriteria, ProfileVo, ProfileEntity> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProfileRepository repository;

	@Override
	public ProfileVo getById(Long id) {
		if (null == id) {
			return null;
		}
		return convert(repository.findOne(id));
	}

	@Modifying
	@Transactional
	@Override
	public Long createOrUpdate(ProfileVo vo) {
		return repository.saveAndFlush(beanMapper.map(vo, ProfileEntity.class)).getId();
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
	public List<ProfileVo> getAll() {
		return convert(repository.findAll());
	}

	@Transactional
	public List<ProfileVo> searchByQuery(String query, Integer maximum) {
		return convert(repository.searchByQuery(query, new PageRequest(0, maximum)));
	}

	@Transactional
	@Override
	public SearchResult<ProfileVo> searchByCriteria(ProfileCriteria criteria, Long startIndex, Long maxResults) {
		Specification<ProfileEntity> spec = ProfileSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<ProfileEntity> page = repository.findAll(spec, paginator);
		SearchResult<ProfileVo> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(convert(page.getContent()));
		return result;
	}

	@Override
	public List<ProfileVo> convert(List<ProfileEntity> entities) {
		return convert(entities, ProfileVo.class);
	}

	@Override
	public ProfileVo convert(ProfileEntity entity) {
		return convert(entity, ProfileVo.class);
	}
}
