package com.spotify.downloader.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class YoutubeService {

    private static final Long NUMBER_OF_VIDEOS_RETURNED = 10L;
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    private final YouTube youTube;

    @Value("${youtube.key}")
    private String youtubeKey;

    public YoutubeService(YouTube youTube) {
        this.youTube = youTube;
    }

    public SearchListResponse search(String keyword) {

        try {
            YouTube.Search.List search = youTube.search().list("id,snippet");

            search.setKey(youtubeKey);
            search.setQ(keyword);

            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            return search.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getYoutubeUrl(String keyword) {
        try {
            YouTube.Search.List search = youTube.search().list("id,snippet");

            search.setKey(youtubeKey);
            search.setQ(keyword);

            search.setFields("items(id/videoId)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            SearchListResponse response = search.execute();

            if (response.getItems().size() > 0 ) {
                return YOUTUBE_BASE_URL + response.getItems().get(0).getId().getVideoId();
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
