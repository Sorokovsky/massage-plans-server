package pro.sorokovsky.massageplansserver.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException {
    private final HttpStatus status;
    private final String messageCode;

    public HttpException(String messageCode, HttpStatus status) {
        this.status = status;
        this.messageCode = messageCode;
        super(messageCode);
    }

    public HttpException(String messageCode, HttpStatus status, Throwable cause) {
        super(messageCode, cause);
        this.status = status;
        this.messageCode = messageCode;
    }
}
