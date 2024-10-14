package hexagonal_test.hexagonal.user.exception;

import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends UserException {
    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordNotMatchException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
