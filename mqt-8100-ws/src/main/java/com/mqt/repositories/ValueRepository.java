package com.mqt.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mqt.pojo.entities.ValueEntity;

/**
 * Value repository
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
@Repository
public interface ValueRepository extends JpaSpecificationExecutor<ValueEntity>, JpaRepository<ValueEntity, Long> {

	/**
	 * Count Entities by Id
	 * 
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM ValueEntity AS v WHERE v.id = :id")
	Integer countById(@Param("id") Long id);

	/**
	 * delete entities by id
	 * 
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM ValueEntity v WHERE v.id = :id")
	void delete(@Param("id") Long id);

	/**
	 * Select all values by instanceId
	 * 
	 * @param instanceId
	 * @param pageable
	 * @return
	 */
	@Query("FROM ValueEntity v where v.instance.id = :instanceId")
	List<ValueEntity> searchByInstanceId(@Param("instanceId") Long instanceId, Pageable pageable);
}
