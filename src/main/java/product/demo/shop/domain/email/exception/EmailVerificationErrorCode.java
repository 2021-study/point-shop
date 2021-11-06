package product.demo.shop.domain.email.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import product.demo.shop.common.exception.ErrorCode;

@Getter
public enum EmailVerificationErrorCode implements ErrorCode {
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알수 없는 서버 에러 입니다."),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "만료된 토큰입니다."),
    ALREADY_USED_TOKEN(HttpStatus.BAD_REQUEST, "이미 사용된 토큰입니다."),
    NOT_FOUND_TOKEN(HttpStatus.BAD_REQUEST, "없는 토큰입니다." );

    private HttpStatus errorStatus;
    private String errorMessage;

    EmailVerificationErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
