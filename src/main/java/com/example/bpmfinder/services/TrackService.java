package com.example.bpmfinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

@Service
public class TrackService {

  @Autowired
  SpotifyService spotifyService;

  public Track getTrack(String trackId) {
    GetTrackRequest getTrackRequest = spotifyService.getSpotifyApi().getTrack(trackId).build();
    Track track = null;
    try {
      track = getTrackRequest.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return track;
  }
}
