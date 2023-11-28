package streamSpring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import streamSpring.dto.response.VideoResponse;
import streamSpring.entities.GenreEntitie;
import streamSpring.entities.VideoEntitie;
import streamSpring.entities.VideoGenreId;
import streamSpring.exceptions.GenreException;
import streamSpring.exceptions.VideoException;
import streamSpring.repository.GenreRepository;
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
    @Autowired
    VideoGenreIdService videoGenreIdService;
    @Autowired
    GenreRepository genreRepository;

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

    public  VideoEntitie createVideo(VideoEntitie videoEntitie){
        if (videoRepository.existsById(videoEntitie.getTitle())){
            throw new VideoException("Une vidéo existe déja sous ce titre");
        }
        return videoRepository.save(videoEntitie);
    }

    public  VideoEntitie updateVideo(VideoEntitie videoEntitie){
        if (videoEntitie.getTitle().isBlank()){
            throw new VideoException("Une vidéo existe déja sous ce titre");
        }
        return videoRepository.save(videoEntitie);
    }
    public Page<VideoEntitie> findAllByPage(Integer numPage, Integer videoParPage){
        PageRequest pageRequest = PageRequest.of(numPage, videoParPage);
        return videoRepository.findAll(pageRequest);
    }

    public List<VideoResponse> findAllVideoByGenre(String genreString){
        GenreEntitie genreEntitie = genreRepository.findById(genreString).orElseThrow(() ->
                new GenreException("genre non trouvé"));
        List<VideoGenreId> videoGenreIds = videoGenreIdService.findByGenre(genreEntitie);
        List<VideoEntitie> videoEntities = videoGenreIds.stream().map(VideoGenreId::getVideoEntitie).toList();
        List<VideoResponse> videoResponses = videoEntities.stream().map(VideoResponse::new).toList();
        return videoResponses;
    }

}
