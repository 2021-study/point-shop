package product.demo.shop.common.mail;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import product.demo.shop.common.exception.ErrorCode;

@Getter
public enum EmailErrorCode implements ErrorCode {
    EMAIL_UNKNOWN_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "서버에러 - 알수없는 오류가 발생하였습니다."),
    EMAIL_SERVER_AUTH_FAIL(HttpStatus.BAD_REQUEST, "메일인증실패 - 요청값 확인해주세요"),
    EMAIL_SEND_PROCESS_ERROR(HttpStatus.BAD_GATEWAY, "메일전송처리 중 오류가 발생하였습니다.")
    ;

    private HttpStatus errorStatus;
    private String errorMessage;

    EmailErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }
}
