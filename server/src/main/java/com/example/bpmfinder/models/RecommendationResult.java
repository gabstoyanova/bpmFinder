package com.example.bpmfinder.models;

import lombok.Getter;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

@Getter
public class RecommendationResult {

  private final String trackName;
  private final String spotifyUrl;

  private float acousticness;
  private float danceability;
  private float energy;
  private float instrumentalness;
  private float tempo;
  private int timeSignature;

  public RecommendationResult(TrackSimplified track) {
    this.trackName = track.getName();
    this.spotifyUrl = track.getExternalUrls().getExternalUrls().get("spotify");
  }

  public void setAudioFeatureAttributes(AudioFeatures audioFeatures) {
    this.acousticness = audioFeatures.getAcousticness();
    this.danceability = audioFeatures.getDanceability();
    this.energy = audioFeatures.getEnergy();
    this.instrumentalness = audioFeatures.getInstrumentalness();
    this.tempo = audioFeatures.getTempo();
    this.timeSignature = audioFeatures.getTimeSignature();
  }

}
