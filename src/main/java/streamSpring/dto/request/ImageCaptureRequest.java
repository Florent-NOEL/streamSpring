package streamSpring.dto.request;

public class ImageCaptureRequest {
    String timeCapture;
    String videoName;

    public ImageCaptureRequest() {
    }

    public ImageCaptureRequest(String timeCapture, String videoName) {
        this.timeCapture = timeCapture;
        this.videoName = videoName;
    }

    public String getTimeCapture() {
        return timeCapture;
    }

    public void setTimeCapture(String timeCapture) {
        this.timeCapture = timeCapture;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
