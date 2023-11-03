package streamSpring.dto.request;

import java.util.List;

public class VideoRequest {
    String title;
    List<String> GenreEntitieId;
    public VideoRequest() {
    }

    public VideoRequest(String title, List<String> genreEntitieId) {
        this.title = title;
        GenreEntitieId = genreEntitieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGenreEntitieId() {
        return GenreEntitieId;
    }

    public void setGenreEntitieId(List<String> genreEntitieId) {
        GenreEntitieId = genreEntitieId;
    }
}
