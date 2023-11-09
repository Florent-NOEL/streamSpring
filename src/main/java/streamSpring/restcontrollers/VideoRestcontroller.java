package streamSpring.restcontrollers;



import io.swagger.v3.core.util.Json;
import org.opencv.video.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import streamSpring.dto.request.ImageCaptureRequest;
import streamSpring.dto.request.VideoRequest;
import streamSpring.dto.response.VideoResponse;
import streamSpring.entities.GenreEntitie;
import streamSpring.entities.VideoEntitie;
import streamSpring.entities.VideoGenreId;
import streamSpring.exceptions.VideoException;
import streamSpring.models.UrlList;
import streamSpring.services.GenreService;
import streamSpring.services.VideoGenreIdService;
import streamSpring.services.VideoService;


import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/video")
public class VideoRestcontroller {
    @Autowired
    VideoService videoService;
    @Autowired
    GenreService genreService;
    @Autowired
    VideoGenreIdService videoGenreIdService;
    @GetMapping("/test")
    public String testFuction() {
        System.out.println("hello");
        return "hello";
    }
    @GetMapping("/title/{video_title}")
    public VideoResponse findVideoByTitle(@PathVariable("video_title") String videoTitle){
       VideoEntitie videoEntitie = videoService.findByNom(videoTitle);
       return new VideoResponse(videoEntitie);
    }

@PostMapping("/create")
@ResponseStatus(HttpStatus.CREATED)
public String createVideo(@RequestBody VideoRequest videoRequest){
    if (videoRequest.getTitle() ==null || videoRequest.getTitle().isBlank()){
        throw new VideoException("la video n'a pas de titre");
    }
    convertVideoRequestToEntity(videoRequest);
    return "la video est créer avec succes";
}

public VideoEntitie convertVideoRequestToEntity(VideoRequest videoRequest){
    VideoEntitie videoEntitie = new VideoEntitie();
    videoEntitie.setTitle(videoRequest.getTitle());
    videoEntitie.setType(videoRequest.getType());
    if(videoEntitie.getType().contains("matroska")){
        videoEntitie.setType("video/mkv");
    }
    videoService.updateVideo(videoEntitie);
    if (videoRequest.getGenreEntitieId()!=null){
        List<VideoGenreId> videoGenreIds = videoRequest.getGenreEntitieId().stream().map((s) -> {
            GenreEntitie genreEntitie = genreService.findById(s);
            return  videoGenreIdService.create(new VideoGenreId(videoEntitie,genreEntitie));
        }).collect(Collectors.toList());
        videoEntitie.setVideoGenreIds(videoGenreIds);
    }
    return videoEntitie;
}

    @PostMapping("/uploadVideo")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            // Récupérez le fichier téléchargé et enregistrez-le localement
            byte[] bytes = file.getBytes();
            String fileName = UrlList.getUrlVideoFile() + file.getOriginalFilename();
            FileOutputStream stream = new FileOutputStream(fileName);
            stream.write(bytes);
            stream.close();
            return fileName;
        } catch (IOException e) {
            return "Erreur lors du téléchargement du fichier vidéo.";
        }

    }



    @PostMapping ("/captureImage")
    public String saveImage(@RequestBody ImageCaptureRequest imageCaptureRequest){
        try {
           String timeCapture = imageCaptureRequest.getTimeCapture();
            String videoName = imageCaptureRequest.getVideoName();
            System.out.println(timeCapture);
            System.out.println(videoName);
            //video name doit contenir le type .mp4 ...
            String imgName = videoName+"_Image"+".png";
            System.out.println(imgName);
            String videoUrl = UrlList.getUrlVideoFile()+videoName; // Remplacez par l'URL de votre vidéo
            String outputPath = UrlList.getUrlImageFile()+ imgName; // Le chemin de sortie de l'image capturée
            File imageFileExist = new File(outputPath);
            if (imageFileExist.exists() && imageFileExist.isFile()) {
                imageFileExist.delete(); // Supprime le fichier s'il existe
            }
            captureImage(videoUrl, outputPath, timeCapture);
            System.out.println("Image capturée avec succès à  x secondes de la vidéo.");
            return outputPath;
        } catch (Exception e) {
            e.printStackTrace();
            return "faild to capture the img";
        }
    }

    public void captureImage(String videoUrl, String outputPath, String timeCapture) throws IOException, InterruptedException {
        // Chemin vers l'exécutable FFmpeg
        String ffmpegPath = "C:\\PATH_Programs\\ffmpeg.exe"; // Remplacez par le chemin de l'exécutable FFmpeg sur votre système
        // Commande FFmpeg pour capturer une image à "timeCapture" secondes
        String[] cmd = { ffmpegPath, "-i", videoUrl, "-ss", timeCapture, "-vframes", "1", outputPath };

        // Exécute la commande FFmpeg
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process process = processBuilder.start();

        // Attendez que la commande se termine
        process.waitFor(50, TimeUnit.SECONDS);
        process.destroy();
        System.out.println("process end");
    }


    @GetMapping("/video_page{num}+{items}")
    public List<VideoResponse> findByPage(@PathVariable Integer num, @PathVariable Integer items){
        List<VideoResponse> videoResponses = videoService.findAllByPage(num,items).map(VideoResponse::new).stream().collect(Collectors.toList());
        return  videoResponses;
    }



}





