package fr.diginamic.spring.exception;

public class FunctionalException extends Exception {
  public FunctionalException(String message) {
    super("FunctionalException : " +message);
  }
}