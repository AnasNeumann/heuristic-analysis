package com.mqt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mqt.criteria.MessageCriteria;
import com.mqt.pojo.SearchResult;
import com.mqt.pojo.entities.MessageEntity;
import com.mqt.pojo.vo.MessageVo;
import com.mqt.repositories.MessageRepository;
import com.mqt.specifications.MessageSpecification;

/**
 * data access object service
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/08/2017
 * @verion 1.0
 */
@Service("messageService")
public class MessageService extends GenericCrudService<MessageCriteria, MessageVo, MessageEntity> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MessageRepository repository;

	@Override
	public MessageVo getById(Long id) {
		return convert(repository.findOne(id));
	}

	@Modifying
	@Transactional
	@Override
	public Long createOrUpdate(MessageVo vo) {
		return repository.saveAndFlush(beanMapper.map(vo, MessageEntity.class)).getId();
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
	public List<MessageVo> getAll() {
		return convert(repository.findAll());
	}

	@Transactional
	@Override
	public SearchResult<MessageVo> searchByCriteria(MessageCriteria criteria, Long startIndex, Long maxResults) {
		Specification<MessageEntity> spec = MessageSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<MessageEntity> page = repository.findAll(spec, paginator);
		SearchResult<MessageVo> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(convert(page.getContent()));
		return result;
	}

	@Override
	public List<MessageVo> convert(List<MessageEntity> entities) {
		return convert(entities, MessageVo.class);
	}

	@Override
	public MessageVo convert(MessageEntity entity) {
		return convert(entity, MessageVo.class);
	}
}
