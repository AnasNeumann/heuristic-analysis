package com.mqt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mqt.criteria.UserAccountCriteria;
import com.mqt.pojo.AuthException;
import com.mqt.pojo.SearchResult;
import com.mqt.pojo.entities.UserAccountEntity;
import com.mqt.pojo.vo.UserAccountVo;
import com.mqt.repositories.UserAccountRepository;
import com.mqt.specifications.UserAccountSpecification;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * data access object service
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/08/2107
 * @verion 1.0
 */
@Service("userAccountService")
public class UserAccountService extends GenericCrudService<UserAccountCriteria, UserAccountVo, UserAccountEntity> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserAccountRepository repository;

	@Override
	public UserAccountVo getById(Long id) {
		if (null == id) {
			return null;
		}
		return convert(repository.findOne(id));
	}

	// FIXME faire une requête WHERE mail = AND password = EST UNE FAILLE DE SECU !
	public UserAccountVo connect(String mail, String password) {
		List<UserAccountEntity> listResult = repository.getByLoginAndPassword(mail, password);
		if (isNotEmpty(listResult)) {
			return convert(listResult.get(0));
		}
		return null;
	}

	public UserAccountVo getByAccessToken(Long id, String access) {
		UserAccountVo user = convert(repository.findOne(id));
		if (user.getAccessToken().equals(access) && user.getState().equals(1)) {
			return user;
		}
		throw new AuthException("access token expired");
	}

	public UserAccountVo getByRefreshToken(Long id, String refresh) {
		UserAccountVo user = convert(repository.findOne(id));
		if (user.getRefreshToken().equals(refresh)) {
			return user;
		}
		throw new AuthException("access token expired");
	}

	@Modifying
	@Transactional
	@Override
	public Long createOrUpdate(UserAccountVo vo) {
		return repository.saveAndFlush(beanMapper.map(vo, UserAccountEntity.class)).getId();
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
	public List<UserAccountVo> getAll() {
		return convert(repository.findAll());
	}

	@Transactional
	@Override
	public SearchResult<UserAccountVo> searchByCriteria(UserAccountCriteria criteria, Long startIndex,
			Long maxResults) {
		Specification<UserAccountEntity> spec = UserAccountSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<UserAccountEntity> page = repository.findAll(spec, paginator);
		SearchResult<UserAccountVo> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(convert(page.getContent()));
		return result;
	}

	@Override
	public List<UserAccountVo> convert(List<UserAccountEntity> entities) {
		return convert(entities, UserAccountVo.class);
	}

	@Override
	public UserAccountVo convert(UserAccountEntity entity) {
		return convert(entity, UserAccountVo.class);
	}
}
