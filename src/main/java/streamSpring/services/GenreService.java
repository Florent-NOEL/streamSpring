package streamSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import streamSpring.entities.GenreEntitie;
import streamSpring.entities.VideoEntitie;
import streamSpring.entities.VideoGenreId;
import streamSpring.exceptions.VideoException;
import streamSpring.repository.GenreRepository;
import streamSpring.repository.VideoGenreIdRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    VideoGenreIdRepository videoGenreIdRepository;

    public List<GenreEntitie> findAll(){
        return genreRepository.findAll();
    }
    public List<GenreEntitie> findByVideo(VideoEntitie videoEntitie){
        if (videoEntitie.getTitle() == null || videoEntitie.getTitle().isBlank()){
            throw new VideoException("video title is blank");
        }
        List<VideoGenreId> videoGenreIds = videoGenreIdRepository.findByVideoEntitie(videoEntitie);
        List<GenreEntitie> genreEntities = videoGenreIds.stream().map(VideoGenreId::getGenreEntitie).collect(Collectors.toList());
        return genreEntities;
    }


}
