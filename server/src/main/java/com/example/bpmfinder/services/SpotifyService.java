package com.example.bpmfinder.services;

import com.example.bpmfinder.exceptions.SpotifyApiException;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.AbstractDataRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

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

  private String code = "";

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

  public <T> T executeRequest(AbstractDataRequest<T> request) throws SpotifyApiException {
    T object;
    try {
      object = request.execute();
    } catch (Exception e) {
      e.printStackTrace();
      throw new SpotifyApiException(e.getMessage());
    }
    return object;
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

  public void authorizationCode_Sync(String userCode) {
    code = userCode;
    try {
      final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

      // Set access and refresh token for further "spotifyApi" object usage
      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
      spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }

  }

  public String authorizationCodeUri_Sync() {
    AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//          .scope("user-read-email")
          .show_dialog(true)
      .build();
    final URI uri = authorizationCodeUriRequest.execute();

    System.out.println("URI: " + uri.toString());
    return uri.toString();
  }

  public User getCurrentUsersProfile_Sync() throws SpotifyApiException {
    GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
    return executeRequest(getCurrentUsersProfileRequest);
  }



  // i can leave the auth methods here 💡. Work with components can be moved to services of their own,
  // the below methods are just me playing around with the api for now
  public Playlist getPlaylist(String playlistId) throws SpotifyApiException {
    GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(playlistId).build();
    return executeRequest(getPlaylistRequest);
  }

}
