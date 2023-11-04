package streamSpring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "données invalides")
public class GenreException extends RuntimeException {

    public GenreException(String message) {
        super(message);
    }
}
