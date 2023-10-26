package restcontroller;

import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/api/video")
@CrossOrigin(origins = "*")
public class VideoRestcontroller {

    @PostMapping("/upload")
    public void uploadVideo (@RequestBody File videoFile){
        VideoFileCopy(videoFile);

    }

    public void VideoFileCopy(File videoFile) {
        String destinationPath = "src/main/resources/testvideo.webm";


            File destinationFile = new File(destinationPath);

            try (InputStream inputStream = new FileInputStream(videoFile);
                 OutputStream outputStream = new FileOutputStream(destinationFile)) {
                    outputStream.write(inputStream.readAllBytes());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        System.out.println("Copie terminée.");



    }












}
