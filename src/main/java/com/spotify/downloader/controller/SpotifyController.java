package com.spotify.downloader.controller;

import com.spotify.downloader.service.SpotifyService;
import com.spotify.downloader.service.YoutubeService;
import com.wrapper.spotify.model_objects.specification.Playlist;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpotifyController {

    private final SpotifyService spotifyService;
    private final YoutubeService youtubeService;

    public SpotifyController(SpotifyService spotifyService, YoutubeService youtubeService) {
        this.spotifyService = spotifyService;
        this.youtubeService = youtubeService;
    }

    @RequestMapping("/spotify/playlist/{playlistId}")
    public List<String> index(@PathVariable String playlistId) {
        List<String> videos = spotifyService.getYoutubeUrls(playlistId);
        videos.forEach(youtubeService::getVideo);
        return videos;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
