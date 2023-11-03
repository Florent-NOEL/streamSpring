package streamSpring.services;

import org.opencv.video.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import streamSpring.entities.VideoEntitie;
import streamSpring.exceptions.VideoException;
import streamSpring.repository.VideoRepository;

import java.util.List;

@Service
public class VideoService {
    @Autowired
    VideoRepository videoRepository;

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

}
