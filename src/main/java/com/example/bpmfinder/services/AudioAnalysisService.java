package com.example.bpmfinder.services;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.miscellaneous.AudioAnalysis;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioAnalysisForTrackRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;

import java.io.IOException;

@Service
public class AudioAnalysisService {

  @Autowired
  SpotifyService spotifyService;

  public AudioAnalysis getTrackAnalysis(String trackId) {
    GetAudioAnalysisForTrackRequest getAudioAnalysisForTrackRequest = spotifyService.getSpotifyApi().getAudioAnalysisForTrack(trackId).build();
    AudioAnalysis audioAnalysis = null;
    try {
      audioAnalysis = getAudioAnalysisForTrackRequest.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return audioAnalysis;
  }

  public AudioFeatures getTrackAudioFeatures(String trackId) {
    GetAudioFeaturesForTrackRequest getAudioFeaturesForTrackRequest = spotifyService.getSpotifyApi().getAudioFeaturesForTrack(trackId).build();
    AudioFeatures audioFeatures = null;
    try {
      audioFeatures = getAudioFeaturesForTrackRequest.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return audioFeatures;
  }

}
