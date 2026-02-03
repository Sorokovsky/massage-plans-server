package pro.sorokovsky.massageplansserver.exception.user;

import pro.sorokovsky.massageplansserver.exception.base.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    private static final String MESSAGE_CODE = "errors.user.already-exists";

    public UserAlreadyExistsException() {
        super(MESSAGE_CODE);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(MESSAGE_CODE, cause);
    }
}
