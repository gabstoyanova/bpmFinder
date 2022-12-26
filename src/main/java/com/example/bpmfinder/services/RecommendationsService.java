package com.example.bpmfinder.services;

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
  AudioAnalysisService audioAnalysisService;

  /**
   * Get recommendations by a seed track id
   * todo: idea: if (authenticated user) search in user's playlists for tracks with the same characteristics and prioritize them
   */
  public List<RecommendationResult> getRecommendationsByTrackId(String trackId) {
    AudioFeatures audioFeatures = audioAnalysisService.getTrackAudioFeatures(trackId);

    GetRecommendationsRequest getRecommendationsRequest = spotifyService.getSpotifyApi().getRecommendations()
      .limit(10)
      .seed_tracks(trackId)
      .target_tempo(audioFeatures.getTempo())
      .min_acousticness((float) (audioFeatures.getAcousticness() - 0.1))
      .max_acousticness((float) (audioFeatures.getAcousticness() + 0.1))
//      todo: decide which parameters i am going to match by
//      .target_danceability(audioFeatures.getDanceability())
//      .seed_genres(String.join(",", genreIds))
      .min_popularity(20)
      .build();

    Recommendations recommendations = executeGetRecommendationsRequest(getRecommendationsRequest);
    return constructRecommendationResults(recommendations);
  }

  public Recommendations executeGetRecommendationsRequest(GetRecommendationsRequest getRecommendationsRequest) {
    Recommendations recommendations = null;
    try {
      recommendations = getRecommendationsRequest.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return recommendations;
  }

  private List<RecommendationResult> constructRecommendationResults(Recommendations recommendations) {
    List<RecommendationResult> recommendationResults = new ArrayList<>();

    for (TrackSimplified track : recommendations.getTracks()) {
      RecommendationResult recommendationResult = new RecommendationResult(track);
      recommendationResult.setAudioFeatureAttributes(audioAnalysisService.getTrackAudioFeatures(track.getId()));
      recommendationResults.add(recommendationResult);
    }
    return recommendationResults;
  }
}
