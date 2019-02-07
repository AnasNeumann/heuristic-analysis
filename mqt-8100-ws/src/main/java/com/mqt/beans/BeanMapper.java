package com.mqt.beans;

import java.io.Serializable;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;


/**
 * Created by ineumann on 9/23/17.
 */
public class BeanMapper implements Serializable {
  private static final long serialVersionUID = 1L;

  private static BeanMapper instance;

  private MapperFactory mapperFactory;
  private MapperFacade mapper;

  /**
   * private constructor.
   */
  private BeanMapper() {
    mapperFactory = new DefaultMapperFactory.Builder().build();
    mapper = mapperFactory.getMapperFacade();
  }

  /**
   * Access of single instance of class.
   *
   * @return BeanMapperSingleton
   */
  public static BeanMapper getInstance() {
    if (null == instance) {
      instance = new BeanMapper();
    }

    return instance;
  }

  /**
   * Copy of object in an instance of the destination class.
   *
   * @param source
   * @param clazz
   * @return T instance
   */
  public <T> T map(Object source, Class<T> clazz) {
    return mapper.map(source, clazz);
  }

  /**
   * Copy of object in an instance in the destination instance.
   *
   * @param source
   * @param dest
   * @return T instance
   */
  public void map(Object source, Object dest) {
    mapper.map(source, dest);
  }
}
