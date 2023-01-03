package com.example.bpmfinder.controllers;

import com.example.bpmfinder.exceptions.SpotifyApiException;
import com.example.bpmfinder.models.RecommendationResult;
import com.example.bpmfinder.services.RecommendationsService;
import com.example.bpmfinder.services.SpotifyService;
import com.example.bpmfinder.services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.model_objects.miscellaneous.AudioAnalysis;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;

@RestController
@RequestMapping("/tracks")
@CrossOrigin("http://localhost:3000")
public class TrackController {

  @Autowired
  SpotifyService spotifyService;

  @Autowired
  TrackService trackService;

  @Autowired
  RecommendationsService recommendationsService;

  @GetMapping("/{trackId}")
  public Track getTrack(@PathVariable String trackId) throws SpotifyApiException {
    return trackService.getTrack(trackId);
  }

  @GetMapping("/{trackId}/audioAnalysis")
  public AudioAnalysis getTrackAnalysis(@PathVariable String trackId) throws SpotifyApiException {
    return trackService.getTrackAnalysis(trackId);
  }

  @GetMapping("/{trackId}/audioFeatures")
  public AudioFeatures getTrackAudioFeatures(@PathVariable String trackId) throws SpotifyApiException {
    return trackService.getTrackAudioFeatures(trackId);
  }

  //  todo: idea: pass the desired count of recommendations (could be in range 1 - 100) as a query param
  @GetMapping("/{trackId}/recommendations")
  public List<RecommendationResult> getRecommendationsByTrack(@PathVariable String trackId) throws SpotifyApiException {
    return recommendationsService.getRecommendationsByTrackId(trackId);
  }
}
