package pro.sorokovsky.massageplansserver.exception.user;

import pro.sorokovsky.massageplansserver.exception.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    private static final String MESSAGE_CODE = "errors.user.not-found";

    public UserNotFoundException() {
        super(MESSAGE_CODE);
    }

    public UserNotFoundException(Throwable cause) {
        super(MESSAGE_CODE, cause);
    }
}
