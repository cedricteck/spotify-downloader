package com.spotify.downloader.controller;

import com.spotify.downloader.service.SpotifyService;
import com.wrapper.spotify.model_objects.specification.Playlist;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpotifyController {

    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @RequestMapping("/spotify/playlist/{playlistId}")
    public List<String> index(@PathVariable String playlistId) {
        return spotifyService.getYoutubeUrls(playlistId);
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
