package streamSpring.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table
public class VideoGenreId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name= "videoGenreId_video", foreignKey = @ForeignKey(name = "videoGenreId_video_fk"))
    private  VideoEntitie videoEntitie;
    @ManyToOne
    @JoinColumn(name= "videoGenreId_genre", foreignKey = @ForeignKey(name = "videoGenreId_genre_fk"))
    private GenreEntitie genreEntitie;

    public VideoGenreId() {
    }

    public VideoGenreId(VideoEntitie videoEntitie, GenreEntitie genreEntitie) {
        this.videoEntitie = videoEntitie;
        this.genreEntitie = genreEntitie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VideoEntitie getVideoEntitie() {
        return videoEntitie;
    }

    public void setVideoEntitie(VideoEntitie videoEntitie) {
        this.videoEntitie = videoEntitie;
    }

    public GenreEntitie getGenreEntitie() {
        return genreEntitie;
    }

    public void setGenreEntitie(GenreEntitie genreEntitie) {
        this.genreEntitie = genreEntitie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoGenreId that = (VideoGenreId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
