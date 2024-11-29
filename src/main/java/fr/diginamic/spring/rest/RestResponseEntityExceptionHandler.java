package fr.diginamic.spring.rest;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends Exception {

  public RestResponseEntityExceptionHandler() {
  }

  public RestResponseEntityExceptionHandler(String message) {
    super(message);
  }
}
