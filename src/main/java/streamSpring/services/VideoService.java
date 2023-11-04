package streamSpring.services;

import org.opencv.video.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import streamSpring.entities.GenreEntitie;
import streamSpring.entities.VideoEntitie;
import streamSpring.entities.VideoGenreId;
import streamSpring.exceptions.VideoException;
import streamSpring.repository.VideoGenreIdRepository;
import streamSpring.repository.VideoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    VideoGenreIdRepository videoGenreIdRepository;

    public List<VideoEntitie> findAll(){
       return videoRepository.findAll();
    }
    //public List<VideoEntitie> findByNomContaining(String nom) {
       // return videoRepository.findByNomContaining(nom);
   // }

    public  VideoEntitie findByNom(String nom){
        return videoRepository.findById(nom).orElseThrow(() ->
                new VideoException("video non trouvé"));
    }

    public List<VideoEntitie> findByGenre(GenreEntitie genreEntitie){
        List<VideoGenreId> videoGenreIds = videoGenreIdRepository.findByGenreEntitie(genreEntitie);
        List<VideoEntitie> genreEntities = videoGenreIds.stream().map(VideoGenreId::getVideoEntitie).collect(Collectors.toList());
        return genreEntities;
    }

}
