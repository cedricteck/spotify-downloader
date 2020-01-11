package com.spotify.downloader.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class YoutubeService {

    private static final Long NUMBER_OF_VIDEOS_RETURNED = 10L;
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    private Logger logger = LoggerFactory.getLogger(YoutubeService.class);

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
                return response.getItems().get(0).getId().getVideoId();
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getVideo(String videoId) {
        try {
            YoutubeDLRequest youtubeDLRequest = new YoutubeDLRequest(YOUTUBE_BASE_URL + videoId, System.getProperty("user.home"));
            youtubeDLRequest.setOption("ignore-errors");
            youtubeDLRequest.setOption("output", "%(title)s.%(ext)s");
            youtubeDLRequest.setOption("retries", 10);
            youtubeDLRequest.setOption("extract-audio");
            youtubeDLRequest.setOption("audio-format", "mp3");

            YoutubeDLResponse response = YoutubeDL.execute(youtubeDLRequest, (progress, etaInSeconds) -> logger.info(progress + "%"));

            logger.info(response.getOut());

        } catch (YoutubeDLException e) {
            e.printStackTrace();
        }

    }
}
