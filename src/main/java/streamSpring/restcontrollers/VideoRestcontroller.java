package streamSpring.restcontrollers;



import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import streamSpring.models.UrlList;



import java.io.*;


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
            String fileName = UrlList.getUrlVideoFile() + file.getOriginalFilename();
            FileOutputStream stream = new FileOutputStream(fileName);
            stream.write(bytes);
            stream.close();
            return fileName;
        } catch (IOException e) {
            return "Erreur lors du téléchargement du fichier vidéo.";
        }

    }


    @GetMapping("/captureImage")
    public String saveImage(){
        try {
            String imgName = "hero"+".png";
            String videoName="hero.webm";
            String videoUrl = UrlList.getUrlVideoFile()+videoName; // Remplacez par l'URL de votre vidéo
            String outputPath = UrlList.getUrlImageFile()+ imgName; // Le chemin de sortie de l'image capturée

            captureImage(videoUrl, outputPath);
            System.out.println("Image capturée avec succès à  x secondes de la vidéo.");
            return outputPath;
        } catch (Exception e) {
            e.printStackTrace();
            return "faild to capture the img";
        }
    }

    public void captureImage(String videoUrl, String outputPath) throws IOException, InterruptedException {
        // Chemin vers l'exécutable FFmpeg
        String ffmpegPath = "C:\\PATH_Programs\\ffmpeg.exe"; // Remplacez par le chemin de l'exécutable FFmpeg sur votre système
        String time = "5";
        // Commande FFmpeg pour capturer une image à "time" secondes
        String[] cmd = { ffmpegPath, "-i", videoUrl, "-ss", time, "-vframes", "1", outputPath };

        // Exécute la commande FFmpeg
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process process = processBuilder.start();

        // Attendez que la commande se termine
        process.waitFor();
    }



}





