package com.spotify.downloader.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyService {

    private final SpotifyApi spotifyApi;
    private final YoutubeService youtubeService;
    private final ClientCredentialsRequest clientCredentialsRequest;

    public SpotifyService(SpotifyApi spotifyApi, YoutubeService youtubeService, ClientCredentialsRequest clientCredentialsRequest) {
        this.spotifyApi = spotifyApi;
        this.youtubeService = youtubeService;
        this.clientCredentialsRequest = clientCredentialsRequest;
    }

    private void refreshToken() {
        try {
             ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Playlist getPlaylist(String playlistId) {
        refreshToken();
        try {
            return spotifyApi.getPlaylist(playlistId).build().execute();
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getYoutubeUrls(String playlistId) {
        Playlist playlist = getPlaylist(playlistId);
        if (playlist != null) {
            ArrayList<String> urls = new ArrayList<>();
            for (int i = 0; i < playlist.getTracks().getItems().length; i++) {
                Track track = playlist.getTracks().getItems()[i].getTrack();
                StringBuilder artistNames = new StringBuilder();
                for (int j = 0; j < track.getArtists().length; j++) {
                    artistNames.append(" ").append(track.getArtists()[j].getName());
                }
                urls.add(youtubeService.getYoutubeUrl(playlist.getTracks().getItems()[i].getTrack().getName() + " " + artistNames.toString()));
            }
            return urls;
        } else {
            return null;
        }
    }
}
