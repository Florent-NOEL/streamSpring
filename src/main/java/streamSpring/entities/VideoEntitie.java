package streamSpring.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "video")
public class VideoEntitie {
    @Id
    String title;
    @Column(name = "type")
    String type;
    @OneToMany(mappedBy = "videoEntitie")
    List<VideoGenreId>  videoGenreIds;

    public VideoEntitie() {
    }

    public VideoEntitie(String title) {
        this.title = title;
    }

    public VideoEntitie(String title, String type, List<VideoGenreId> videoGenreIds) {
        this.title = title;
        this.type = type;
        this.videoGenreIds = videoGenreIds;
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

    public List<VideoGenreId> getVideoGenreIds() {
        return videoGenreIds;
    }

    public void setVideoGenreIds(List<VideoGenreId> videoGenreIds) {
        this.videoGenreIds = videoGenreIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoEntitie that = (VideoEntitie) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
