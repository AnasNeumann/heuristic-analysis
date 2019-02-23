package com.mqt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mqt.pojo.entities.EstimateEntity;

/**
 * Estimate repository 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @version 1.0
 */
@Repository
public interface EstimateRepository
    extends JpaSpecificationExecutor<EstimateEntity>, JpaRepository<EstimateEntity, Long> {

  /**
   * Count Entities by Id
   * @param id
   * @return total by id
   */
  @Query("SELECT COUNT(*) FROM EstimateEntity AS e WHERE e.id = :id")
  Integer countById(@Param("id") Long id);

  /**
   * delete entities by id
   * @param id
   */
  @Modifying
  @Query("DELETE FROM EstimateEntity e WHERE e.id = :id")
  void delete(@Param("id") Long id);
}
