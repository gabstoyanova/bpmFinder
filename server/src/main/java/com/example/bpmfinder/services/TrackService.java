package com.example.bpmfinder.services;

import com.example.bpmfinder.exceptions.SpotifyApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.miscellaneous.AudioAnalysis;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioAnalysisForTrackRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

@Service
public class TrackService {

  @Autowired
  SpotifyService spotifyService;

  public Track getTrack(String trackId) throws SpotifyApiException {
    GetTrackRequest getTrackRequest = spotifyService.getSpotifyApi().getTrack(trackId).build();
    return spotifyService.executeRequest(getTrackRequest);
  }

  public AudioAnalysis getTrackAnalysis(String trackId) throws SpotifyApiException {
    GetAudioAnalysisForTrackRequest getAudioAnalysisForTrackRequest = spotifyService.getSpotifyApi().getAudioAnalysisForTrack(trackId).build();
    return spotifyService.executeRequest(getAudioAnalysisForTrackRequest);
  }

  public AudioFeatures getTrackAudioFeatures(String trackId) throws SpotifyApiException {
    GetAudioFeaturesForTrackRequest getAudioFeaturesForTrackRequest = spotifyService.getSpotifyApi().getAudioFeaturesForTrack(trackId).build();
    return spotifyService.executeRequest(getAudioFeaturesForTrackRequest);
  }
}
