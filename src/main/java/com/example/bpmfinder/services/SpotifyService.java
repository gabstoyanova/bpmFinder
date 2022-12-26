package com.example.bpmfinder.services;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;

@Service
public class SpotifyService {

  @Value("${spotify.redirectUriString}")
  private String redirectUriString;

  @Value("${spotify.clientId}")
  private String clientId;

  @Value("${spotify.clientSecret}")
  private String clientSecret;

  private SpotifyApi spotifyApi;

  @PostConstruct
  public void postConstruct() {
    URI redirectUri = SpotifyHttpManager.makeUri(redirectUriString);

    spotifyApi = new SpotifyApi.Builder()
      .setClientId(clientId)
      .setClientSecret(clientSecret)
      .setRedirectUri(redirectUri)
      .build();

    clientCredentials_Sync();
  }

  public SpotifyApi getSpotifyApi() {
    return this.spotifyApi;
  }

  public void clientCredentials_Sync() {
    try {
      final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
      final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

      // Set access token for further "spotifyApi" object usage
      spotifyApi.setAccessToken(clientCredentials.getAccessToken());

      System.out.println("Expires in: " + clientCredentials.getExpiresIn());
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

}
