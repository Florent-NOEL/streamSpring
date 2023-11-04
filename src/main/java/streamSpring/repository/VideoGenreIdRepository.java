package streamSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streamSpring.entities.GenreEntitie;
import streamSpring.entities.VideoEntitie;
import streamSpring.entities.VideoGenreId;

import java.util.List;

public interface VideoGenreIdRepository extends JpaRepository<VideoGenreId, Long> {
    List<VideoGenreId> findByVideoEntitie(VideoEntitie videoEntitie);
    List<VideoGenreId> findByGenreEntitie(GenreEntitie genreEntitie);

}
