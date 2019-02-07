package com.mqt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mqt.pojo.entities.MessageEntity;

/**
 * Message repository
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/08/2017
 * @version 1.0
 */
@Repository
public interface MessageRepository
    extends JpaSpecificationExecutor<MessageEntity>, JpaRepository<MessageEntity, Long> {

  /**
   * Count Entities by Id
   * @param id
   * @return total by id
   */
  @Query("SELECT COUNT(*) FROM MessageEntity AS m WHERE m.id = :id")
  Integer countById(@Param("id") Long id);

  /**
   * delete entities by id
   * @param id
   */
  @Modifying
  @Query("DELETE FROM MessageEntity m WHERE m.id = :id")
  void delete(@Param("id") Long id);
}
