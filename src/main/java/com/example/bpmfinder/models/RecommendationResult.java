package com.example.bpmfinder.models;

import lombok.Getter;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

@Getter
public class RecommendationResult {

  private final String trackName;
  private final String spotifyUrl;

  private Float acousticness;
  private Float danceability;
  private Float energy;
  private Float instrumentalness;
  private Integer key;
  private Float liveness;
  private Float loudness;
  private Float tempo;

  public RecommendationResult(TrackSimplified track) {
    this.trackName = track.getName();
    this.spotifyUrl = track.getExternalUrls().getExternalUrls().get("spotify");
  }

  public void setAudioFeatureAttributes(AudioFeatures audioFeatures) {
    this.acousticness = audioFeatures.getAcousticness();
    this.danceability = audioFeatures.getDanceability();
    this.energy = audioFeatures.getEnergy();
    this.instrumentalness = audioFeatures.getInstrumentalness();
    this.key = audioFeatures.getKey();
    this.liveness = audioFeatures.getLiveness();
    this.loudness = audioFeatures.getLoudness();
    this.tempo = audioFeatures.getTempo();
  }

}
