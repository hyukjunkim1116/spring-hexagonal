package hexagonal_test.hexagonal.user.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * {
 * "code": "400",
 * "message": "잘못된 요청입니다."
 * }
 */

@Getter
public class ErrorResponse {

    private final Integer code;
    private final String message;

    @Builder
    public ErrorResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

