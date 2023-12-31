package streamSpring.restcontrollers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import streamSpring.dto.request.ImageCaptureRequest;
import streamSpring.dto.request.VideoRequest;
import streamSpring.dto.response.VideoResponse;
import streamSpring.entities.GenreEntitie;
import streamSpring.entities.VideoEntitie;
import streamSpring.entities.VideoGenreId;
import streamSpring.exceptions.GenreException;
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
    if(videoEntitie.getType().contains("matroska")||videoEntitie.getType().contains(".avi")){
        videoEntitie.setType("video/mp4");
        videoEntitie.setTitle(videoRequest.getTitle()+"to.mp4");
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
        //everything work
        try {
            // Récupérez le fichier téléchargé et enregistrez-le localement
            byte[] bytes = file.getBytes();

            String fileName = UrlList.getUrlVideoFile() + file.getOriginalFilename();
            FileOutputStream stream = new FileOutputStream(fileName);
            stream.write(bytes);
            stream.close();
            System.out.println(file.getOriginalFilename());
            if(file.getOriginalFilename().contains(".mkv") || file.getOriginalFilename().contains("avi")){
                convertToMp4(file.getOriginalFilename());
            };
            return fileName;
        } catch (IOException e) {
            return "Erreur lors du téléchargement du fichier vidéo.";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
            if(videoName.contains(".avi")||videoName.contains(".mkv")){
                imgName = videoName+"to.mp4"+"_Image"+".png";
            }
            System.out.println(imgName);
            String videoUrl = UrlList.getUrlVideoFile()+videoName; // Remplacez par l'URL de votre vidéo
            if(videoName.contains(".avi")||videoName.contains(".mkv")){
                videoUrl = videoUrl +"to.mp4";
                System.out.println("video Url"+videoUrl);
            }
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
@GetMapping("/convert_{videoName}")
    public String convertToMp4(@PathVariable("videoName") String videoName) throws IOException, InterruptedException {
    String videoUrl = UrlList.getUrlVideoFile(); // Remplacez par l'URL de votre vidéo

    convertMKVtoMP4(videoName, videoUrl);
        return "down";
    }

    public void convertMKVtoMP4(String videoName, String videoUrl) throws IOException, InterruptedException {

        try {
            String ffmpeg = "ffmpeg -i "+videoName+" -c copy " + videoName+ "to.mp4";
            String[] command = new String[4];
            command[0] = "powershell.exe";
            command[1] = " -Command ";
            command[2] = " cd "+videoUrl+";";
            command[3] = ffmpeg;

           // String[] command = {"C:\\Program Files\\Git\\git-bash.exe","-i"," cd /Documents/wb/videoFile "," mkdir test "};

            // Create the process builder
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // Redirect error stream to output stream
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the command to complete
            int exitCode = process.waitFor();

            // Print the exit code
            System.out.println("Exit Code: " + exitCode);
            process.destroy();
            File deletMkvFile = new File(videoUrl+videoName);
            if (deletMkvFile.exists() && deletMkvFile.isFile()) {
                deletMkvFile.delete(); // Supprime le fichier s'il existe
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }




    @GetMapping("/video_page{num}+{items}")
    public List<VideoResponse> findByPage(@PathVariable Integer num, @PathVariable Integer items){
        List<VideoResponse> videoResponses = videoService.findAllByPage(num,items).map(VideoResponse::new).stream().collect(Collectors.toList());
        return  videoResponses;
    }

    @GetMapping("/find-by-genre/{genre}")
    public List<VideoResponse> findByGenre(@PathVariable String genre){
        if(genre == null || genre.isBlank()){
            throw new GenreException("requested genre is null");
        }
        return videoService.findAllVideoByGenre(genre);
    }


}





