package com.example.bpmfinder.controllers;

import com.example.bpmfinder.exceptions.SpotifyApiException;
import com.example.bpmfinder.services.PlaylistService;
import com.example.bpmfinder.services.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.Playlist;

@RestController
@RequestMapping("/playlists")
@CrossOrigin("http://localhost:3000")
public class PlaylistController {

  @Autowired
  SpotifyService spotifyService;

  @Autowired
  PlaylistService playlistService;

  @GetMapping("/{playlistId}")
  public Playlist getPlaylist(@PathVariable String playlistId) throws SpotifyApiException {
    return playlistService.getPlaylist(playlistId);
  }

  @PostMapping
  public Playlist createPlaylist(@RequestParam("name") String playlistName) throws SpotifyApiException {
    System.out.println(playlistName);
    return playlistService.createPlaylist(playlistName);
  }

  @PostMapping("/{playlistId}")
  public SnapshotResult addTracksToPlaylist(@PathVariable String playlistId, @RequestBody String[] trackIds) throws SpotifyApiException {
    return playlistService.addTracksToPlaylist(playlistId, trackIds);
  }

}
