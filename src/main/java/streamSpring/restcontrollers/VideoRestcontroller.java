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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
            String fileName = "C:\\Users\\flnoel\\Desktop\\Test Developpement\\" + file.getOriginalFilename();
            FileOutputStream stream = new FileOutputStream(fileName);
            stream.write(bytes);
            stream.close();
            return fileName;
        } catch (IOException e) {
            return "Erreur lors du téléchargement du fichier vidéo.";
        }

    }
    @PostMapping("/uploadImage")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImage(@RequestParam("file") MultipartFile fileMultipart) {
        String status = "faild to upload image";

        try {

            BufferedImage image = ImageIO.read(fileMultipart.getInputStream());
            String filepath = "C:\\Users\\flnoel\\Desktop\\Test Developpement\\" + fileMultipart.getName();
            File outputFile = new File(filepath);
            ImageIO.write(image, "jpg", outputFile);
            // Récupérez le fichier téléchargé et enregistrez-le localement
            System.out.println("L'image a été sauvegardée avec succès dans : " + filepath);
            status = ("L'image a été sauvegardée avec succès dans : " + filepath);
        }


        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la sauvegarde de l'image.");
        }
        return status;

    }

    }





