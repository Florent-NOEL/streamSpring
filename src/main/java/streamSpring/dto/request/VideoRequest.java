package streamSpring.dto.request;

import java.util.List;

public class VideoRequest {
    String title;
    String type;
    List<String> GenreEntitieId;
    public VideoRequest() {
    }

    public VideoRequest(String title, String type, List<String> genreEntitieId) {
        this.title = title;
        this.type = type;
        GenreEntitieId = genreEntitieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getGenreEntitieId() {
        return GenreEntitieId;
    }

    public void setGenreEntitieId(List<String> genreEntitieId) {
        GenreEntitieId = genreEntitieId;
    }
}
