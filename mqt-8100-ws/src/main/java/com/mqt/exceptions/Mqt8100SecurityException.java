package com.mqt.exceptions;

/**
 * Created by ineumann on 10/1/17.
 */
public class Mqt8100SecurityException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public Mqt8100SecurityException(String message) {
    super(message);
  }
}

