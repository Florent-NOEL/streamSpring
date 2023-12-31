package streamSpring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "données invalides")
public class VideoException extends RuntimeException {

    public VideoException(String message) {
        super(message);
    }
}
