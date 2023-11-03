package streamSpring.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "genre")
public class GenreEntitie {
    @Id
    private String name;
    @OneToMany(mappedBy = "genreEntitie")
    List<VideoGenreId> videoGenreIds;

    public GenreEntitie() {
    }

    public GenreEntitie(String name, List<VideoGenreId> videoGenreIds) {
        this.name = name;
        this.videoGenreIds = videoGenreIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        GenreEntitie that = (GenreEntitie) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
