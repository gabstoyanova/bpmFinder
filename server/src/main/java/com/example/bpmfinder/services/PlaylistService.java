package com.example.bpmfinder.services;

import com.example.bpmfinder.exceptions.SpotifyApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;

import java.util.Arrays;

@Service
public class PlaylistService {

  @Autowired
  SpotifyService spotifyService;

  public Playlist getPlaylist(String playlistId) throws SpotifyApiException {
    GetPlaylistRequest getPlaylistRequest = spotifyService.getSpotifyApi().getPlaylist(playlistId).build();
    return spotifyService.executeRequest(getPlaylistRequest);
  }

  public Playlist createPlaylist(String name) throws SpotifyApiException {
    CreatePlaylistRequest createPlaylistRequest = spotifyService.getSpotifyApi().createPlaylist(spotifyService.getLoggedInUserId(), name).build();
    return spotifyService.executeRequest(createPlaylistRequest);
  }

  public SnapshotResult addTracksToPlaylist(String playlistId, String[] trackIds) throws SpotifyApiException {
    trackIds = Arrays.stream(trackIds).map(e -> "spotify:track:" + e).toArray(String[]::new);
    AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyService.getSpotifyApi().addItemsToPlaylist(playlistId, trackIds).build();
    return spotifyService.executeRequest(addItemsToPlaylistRequest);
  }
}
