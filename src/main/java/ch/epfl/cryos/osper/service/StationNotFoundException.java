package ch.epfl.cryos.osper.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by kryvych on 17/11/16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException() {
        super();
    }

    public StationNotFoundException(String message) {
        super(message);
    }

    public StationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StationNotFoundException(Throwable cause) {
        super(cause);
    }

    protected StationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
