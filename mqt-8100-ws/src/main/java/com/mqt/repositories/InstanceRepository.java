package com.mqt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mqt.pojo.entities.InstanceEntity;

/**
 * Instance repository
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
@Repository
public interface InstanceRepository
    extends JpaSpecificationExecutor<InstanceEntity>, JpaRepository<InstanceEntity, Long> {

  /**
   * Count Entities by Id
   * @param id
   * @return total by id
   */
  @Query("SELECT COUNT(*) FROM InstanceEntity AS i WHERE i.id = :id")
  Integer countById(@Param("id") Long id);

  /**
   * delete entities by id
   * @param id
   */
  @Modifying
  @Query("DELETE FROM InstanceEntity i WHERE i.id = :id")
  void delete(@Param("id") Long id);
}
