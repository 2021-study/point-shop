package product.demo.shop.domain.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import product.demo.shop.common.exception.ErrorCode;

@Getter
public enum PointShopAuthErrorCode implements ErrorCode {
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알수 없는 서버 에러 입니다."),
    EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저 입니다."),
    MAIL_VERIFICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 검증 코드 생성 중 오류 발생"),
    MAIL_VERIFICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "인증코드 만료입니다. 다시 재인증 해주세요"),
    SIGNUP_COMPLETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "가입 완료 처리 중 에러가 발생하였습니다."),
    NOT_YET_EMAIL_VERIFIED(HttpStatus.BAD_REQUEST, "아직 이메일 인증이 완료되지 않앗습니다.");

    private HttpStatus errorStatus;
    private String errorMessage;

    PointShopAuthErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }
}
