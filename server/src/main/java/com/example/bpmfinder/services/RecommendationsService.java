package com.example.bpmfinder.services;

import com.example.bpmfinder.exceptions.SpotifyApiException;
import com.example.bpmfinder.models.RecommendationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationsService {

  @Autowired
  SpotifyService spotifyService;

  @Autowired
  TrackService trackService;

  /**
   * Get recommendations by a seed track id
   * todo: idea: if (authenticated user) search in user's playlists for tracks with the same characteristics and prioritize them
   */
  public List<RecommendationResult> getRecommendationsByTrackId(String trackId) throws SpotifyApiException {
    AudioFeatures audioFeatures = trackService.getTrackAudioFeatures(trackId);

    GetRecommendationsRequest getRecommendationsRequest = spotifyService.getSpotifyApi().getRecommendations()
      .limit(10)
      .seed_tracks(trackId)

//      set the target bpm with a buffer of +- 2 beats
      .target_tempo(audioFeatures.getTempo())
      .min_tempo(audioFeatures.getTempo() - 2)
      .max_tempo(audioFeatures.getTempo() + 2)
      .min_energy((float) (audioFeatures.getEnergy() - 0.2))
      .max_energy((float) (audioFeatures.getEnergy() + 0.2))

//      we want the tracks with the same time signature as the seed track, otherwise even tracks with the same bpm will feel different in terms of beat
      .target_time_signature(audioFeatures.getTimeSignature())
      .min_time_signature(audioFeatures.getTimeSignature())
      .max_time_signature(audioFeatures.getTimeSignature())

      .min_popularity(30)
      .build();

    Recommendations recommendations = spotifyService.executeRequest(getRecommendationsRequest);
    return constructRecommendationResults(recommendations);
  }

  private List<RecommendationResult> constructRecommendationResults(Recommendations recommendations) throws SpotifyApiException {
    List<RecommendationResult> recommendationResults = new ArrayList<>();

    for (TrackSimplified track : recommendations.getTracks()) {
      RecommendationResult recommendationResult = new RecommendationResult(track);
      recommendationResult.setAudioFeatureAttributes(trackService.getTrackAudioFeatures(track.getId()));
      recommendationResults.add(recommendationResult);
    }
    return recommendationResults;
  }
}
