package pro.sorokovsky.massageplansserver.exception.base;

import org.springframework.http.HttpStatus;

public class ConflictException extends HttpException {
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public ConflictException(String messageCode) {
        super(messageCode, STATUS);
    }

    public ConflictException(String messageCode, Throwable cause) {
        super(messageCode, STATUS, cause);
    }
}
