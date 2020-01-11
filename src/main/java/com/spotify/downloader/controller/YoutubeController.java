package com.spotify.downloader.controller;

import com.google.api.services.youtube.model.SearchListResponse;
import com.spotify.downloader.service.YoutubeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoutubeController {

    private final YoutubeService youtubeService;

    @Value("${youtube.key}")
    private String youtubeKey;

    public YoutubeController(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @RequestMapping("/youtube/search")
    public SearchListResponse search(@RequestBody String keyword) {
        return youtubeService.search(keyword);
    }
}
