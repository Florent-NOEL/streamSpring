package streamSpring.dto.response;


import streamSpring.entities.GenreEntitie;
import streamSpring.entities.VideoEntitie;
import streamSpring.entities.VideoGenreId;

import java.util.List;
import java.util.stream.Collectors;

public class VideoResponse {
    String title;
    List<String> genres;

    public VideoResponse() {
    }
    public  VideoResponse(VideoEntitie videoEntitie){
        this.title = videoEntitie.getTitle();
        if (!videoEntitie.getVideoGenreIds().isEmpty()){
            this.genres = videoEntitie.getVideoGenreIds().stream().map(VideoGenreId::getGenreEntitie).map(GenreEntitie::getName).collect(Collectors.toList());
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
