package pro.sorokovsky.massageplansserver.exception.base;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException {
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(String messageCode) {
        super(messageCode, STATUS);
    }

    public NotFoundException(String messageCode, Throwable cause) {
        super(messageCode, STATUS, cause);
    }
}
