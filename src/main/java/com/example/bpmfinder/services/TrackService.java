package com.example.bpmfinder.services;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

import java.io.IOException;

@Service
public class TrackService {

  @Autowired
  SpotifyService spotifyService;

  public Track getTrack(String trackId) throws IOException, ParseException, SpotifyWebApiException {
    GetTrackRequest getTrackRequest = spotifyService.getSpotifyApi().getTrack(trackId).build();
    final Track track = getTrackRequest.execute();
    System.out.println("Name: " + track.getName());
    return track;
  }
}
