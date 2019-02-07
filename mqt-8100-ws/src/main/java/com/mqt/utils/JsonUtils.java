package com.mqt.utils;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqt.boot.Constantes;

/**
 * Classe d'outils pour la gestion des fichiers Json
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 15/10/2016
 */
public class JsonUtils {
  private static final Logger LOGGER = LogManager.getLogger(JsonUtils.class);

  /**
   * Constructeur privé, classe statique
   */
  private JsonUtils() {}

  /**
   * Convertir une Map en JSON.
   * 
   * @param map
   * @return String
   */
  public static String map2jsonQuietly(Map<?, ?> map) {
    if (!isNotEmpty(map)) {
      return Constantes.EMPTY_STRING;
    }
    String rtn = Constantes.EMPTY_STRING;
    try {
      ObjectMapper mapper = new ObjectMapper();
      rtn = mapper.writeValueAsString(map);
    } catch (IOException e) {
      LOGGER.error("[map2jsonQuietly] Erreur de parsing : msg = {}, type = {}",
          e.getMessage(),
          e.getClass().getSimpleName(),
          e);
    }
    return rtn;
  }

  /**
   * Convertir un objet en JSON.
   * 
   * @param obj
   * @param clazz
   * @return String
   */
  public static String objectTojsonQuietly(Object obj, Class<?> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    String json = null;
    try {
      json = mapper.writeValueAsString(clazz.cast(obj));
    } catch (JsonProcessingException e) {
      LOGGER.error("[objectTojsonQuietly] Erreur de parsing : msg = {}, type = {}",
          e.getMessage(),
          e.getClass().getSimpleName(),
          e);
    }
    return json;
  }

  /**
   * Convertir une Liste en JSON.
   * 
   * @param list
   * @return String
   */
  public static String list2jsonQuietly(List<? extends List<String>> list) {
    if (!isNotEmpty(list)) {
      return Constantes.EMPTY_JSON;
    }
    String rtn = Constantes.EMPTY_JSON;
    try {
      ObjectMapper mapper = new ObjectMapper();
      rtn = mapper.writeValueAsString(list);
    } catch (IOException e) {
      LOGGER.error("[list2jsonQuietly] Erreur de parsing : msg = {}, type = {}",
          e.getMessage(),
          e.getClass().getSimpleName(),
          e);
    }
    return rtn;
  }

  /**
   * Transformer une liste en JSON
   * 
   * @param list
   * @return String
   */
  public static String genericList2jsonQuietly(List<?> list) {
    if (!isNotEmpty(list)) {
      return Constantes.EMPTY_JSON;
    }
    String rtn = Constantes.EMPTY_JSON;
    try {
      ObjectMapper mapper = new ObjectMapper();
      rtn = mapper.writeValueAsString(list);
    } catch (IOException e) {
      LOGGER.error("[genericList2jsonQuietly] Erreur de parsing : msg = {}, type = {}",
          e.getMessage(),
          e.getClass().getSimpleName(),
          e);
    }
    return rtn;
  }

  /**
   * Convertir une chaine Json en map.
   * 
   * @param json
   * @return
   */
  public static Map<String, String> json2mapQuietly(String json) {
    Map<String, String> rtn = null;
    if (isNotEmpty(json)) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        rtn = mapper.readValue(json, new TypeReference<HashMap<String, String>>() {
        });
      } catch (Exception e) {
        LOGGER.error("[json2mapQuietly] Erreur de parsing : msg = {}, type = {}",
            e.getMessage(),
            e.getClass().getSimpleName(),
            e);
      }
    }
    return rtn;
  }

  /**
   * Methode permettant de transformer un message String en un message JSON
   * 
   * @param message
   * @return
   */
  public static String formatJsonMessage(String message) {
    return "{\"message\":\"" + message + "\"}";
  }

}
