package com.mqt.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mqt.pojo.entities.ProfileEntity;

/**
 * PROFILE repository
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
@Repository
public interface ProfileRepository
    extends JpaSpecificationExecutor<ProfileEntity>, JpaRepository<ProfileEntity, Long> {

  /**
   * Count Entities by Id
   * 
   * @param id
   * @return total by id
   */
  @Query("SELECT COUNT(*) FROM ProfileEntity AS p WHERE p.id = :id")
  Integer countById(@Param("id") Long id);

  /**
   * delete entities by id
   * 
   * @param id
   */
  @Modifying
  @Query("DELETE FROM ProfileEntity p WHERE p.id = :id")
  void delete(@Param("id") Long id);

  /**
   * Select all profiles by query
   * 
   * @param query
   * @return
   */
  @Query("FROM ProfileEntity p where (CONCAT(LOWER(p.lastName),' ',LOWER(p.firstName)) LIKE CONCAT('%',LOWER(:query),'%')) OR (CONCAT(LOWER(p.firstName),' ',LOWER(p.lastName)) LIKE CONCAT('%',LOWER(:query),'%') OR LOWER(p.job) LIKE CONCAT('%',LOWER(:query),'%'))")
  List<ProfileEntity> searchByQuery(@Param("query") String query, Pageable pageable);
}
