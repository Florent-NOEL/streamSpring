package streamSpring.restcontrollers;



import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import streamSpring.dto.request.ImageCaptureRequest;
import streamSpring.models.UrlList;



import java.io.*;
import java.util.concurrent.TimeUnit;


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
        process.waitFor(3, TimeUnit.SECONDS);
        process.destroy();
        System.out.println("process end");
    }



}





