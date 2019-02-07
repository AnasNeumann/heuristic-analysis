package com.mqt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mqt.pojo.entities.HeuristicEntity;

/**
 * Heuristic repository
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
@Repository
public interface HeuristicRepository
    extends JpaSpecificationExecutor<HeuristicEntity>, JpaRepository<HeuristicEntity, Long> {

  /**
   * Count Entities by Id
   * @param id
   * @return total by id
   */
  @Query("SELECT COUNT(*) FROM HeuristicEntity AS h WHERE h.id = :id")
  Integer countById(@Param("id") Long id);

  /**
   * delete entities by id
   * @param id
   */
  @Modifying
  @Query("DELETE FROM HeuristicEntity h WHERE h.id = :id")
  void delete(@Param("id") Long id);
}
