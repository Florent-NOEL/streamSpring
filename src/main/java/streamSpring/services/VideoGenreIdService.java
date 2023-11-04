package streamSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import streamSpring.entities.GenreEntitie;
import streamSpring.entities.VideoEntitie;
import streamSpring.entities.VideoGenreId;
import streamSpring.exceptions.GenreException;
import streamSpring.exceptions.VideoException;
import streamSpring.repository.VideoGenreIdRepository;

import java.util.List;

@Service
public class VideoGenreIdService {
    @Autowired
    VideoGenreIdRepository videoGenreIdRepository;

    List<VideoGenreId> findByGenre(GenreEntitie genreEntitie){
    if ((genreEntitie.getName() == null) || (genreEntitie.getName().isBlank()) ){
        throw new GenreException("Genre entitie is blanck");
    }
       return videoGenreIdRepository.findByGenreEntitie(genreEntitie);
    }
    List<VideoGenreId> findByVideo(VideoEntitie videoEntitie){
        if((videoEntitie.getTitle() == null) || (videoEntitie.getTitle().isBlank())){
            throw new VideoException("video title is blank");
        }
        return videoGenreIdRepository.findByVideoEntitie(videoEntitie);
    }
}
