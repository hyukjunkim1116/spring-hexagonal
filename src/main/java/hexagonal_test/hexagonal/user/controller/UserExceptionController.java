package hexagonal_test.hexagonal.user.controller;

import com.example.qna_backend.user.dto.ErrorResponse;
import com.example.qna_backend.user.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionController {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

        // DTO에서 작성 오류 메세지가 defaultMessage에 저장된다.
        // 하나만 지정했으므로 첫번째 값을 가져오면 된다.
        String defaultMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .get();

        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(defaultMessage)
                .build();
    }

    @ResponseBody
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> userException(UserException e) {
        HttpStatus statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(statusCode.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(statusCode)
                .body(body);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {

        ErrorResponse body = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}
