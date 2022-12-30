package com.example.bpmfinder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class SpotifyApiException extends Exception {

  public SpotifyApiException(String message) {
    super(message);
  }
}
