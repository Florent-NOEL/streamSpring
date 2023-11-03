package streamSpring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "video")
public class VideoEntitie {
    @Id
    String title;
    @OneToMany(mappedBy = "videoEntitie")
    List<VideoGenreId>  videoGenreIds;

    public VideoEntitie() {
    }

    public VideoEntitie(String title, List<VideoGenreId> videoGenreIds) {
        this.title = title;
        this.videoGenreIds = videoGenreIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
