package com.example.bpmfinder.controllers;

import com.example.bpmfinder.exceptions.SpotifyApiException;
import com.example.bpmfinder.services.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.model_objects.specification.User;

@RestController
@RequestMapping("/")
@CrossOrigin("http://localhost:3000")
public class UserController {

  @Autowired
  SpotifyService spotifyService;

  @GetMapping("/callback")
  @ResponseBody
  public void callback() {
    System.out.println("reached callback!");
  }

  @GetMapping("/login")
  @ResponseBody
  public String login() {
    return spotifyService.getUserAuthorizationCodeUri();
  }

  @PostMapping("/userCode")
  public void setUserAuthorizationCode(@RequestParam("code") String code) {
    spotifyService.setUserAuthorizationCode(code);
  }

  @GetMapping("/currentUser")
  public User getCurrentUser() throws SpotifyApiException {
    return spotifyService.getCurrentUsersProfile();
  }

}
