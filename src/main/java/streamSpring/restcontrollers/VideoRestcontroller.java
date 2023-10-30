package streamSpring.restcontrollers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import streamSpring.dto.request.VideoRequest;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/video")
public class VideoRestcontroller {
    @GetMapping("/test")
    public String testFuction() {
        System.out.println("hello");
        return "hello";
    }


    @PostMapping("/uploadVideo")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            // Récupérez le fichier téléchargé et enregistrez-le localement
            byte[] bytes = file.getBytes();
            String fileName = "C:\\Users\\Flo\\Documents\\wb\\videoFile\\" + file.getOriginalFilename();
            FileOutputStream stream = new FileOutputStream(fileName);
            stream.write(bytes);
            stream.close();
            return fileName;
        } catch (IOException e) {
            return "Erreur lors du téléchargement du fichier vidéo.";
        }

    }

    @PostMapping("/getVideo")
    public String getVideo(@RequestBody VideoRequest videoRequest) {
        String path = videoRequest.getPath();
        String type = videoRequest.getType();
        path = "C://Users//Flo//Documents//wb//videoFile//hero.webm";
        type = "video/webm";
        System.out.println(path);
        System.out.println(type);
        // Load and return the video file as a Resource.
        Resource videoResource = new FileSystemResource(path);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path)
                .toUriString();

        return fileDownloadUri;
    }

    // Répertoire local où se trouve le fichier vidéo
    private static final String videoDirectory = "C://Users//Flo//Documents//wb//videoFile//";

    @GetMapping("/video/{videoFileName}")
    public ResponseEntity<Resource> serveVideo(@PathVariable String videoFileName) {
        try {
            // Construire le chemin complet du fichier vidéo
            Path videoPath = Paths.get(videoDirectory).resolve(videoFileName).normalize();
            Resource resource = new UrlResource(videoPath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Générez une URL pour lire la vidéo
    @GetMapping("/videoURL/{videoFileName}")
    public String getVideoURL(@PathVariable String videoFileName) {
        String videoUrl = MvcUriComponentsBuilder
                .fromMethodName(VideoRestcontroller.class, "serveVideo", videoFileName)
                .build()
                .toUriString();
        return videoUrl;
    }

}
