package com.spotify.downloader.model;

public class YoutubeSearch {

    private String keyword;

    public YoutubeSearch(){

    }

    public YoutubeSearch(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
