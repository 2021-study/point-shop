package product.demo.shop.domain.pointpolicy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import product.demo.shop.common.exception.ErrorCode;

@Getter
public enum GradePointPolicyErrorCode implements ErrorCode {

    INVALID_MEASUREMENT_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 단위(Measurement)입니다."),
    INVALID_POLICY_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 정책 타입 입니다."),
    DELETE_BEFORE_HOLD(HttpStatus.SERVICE_UNAVAILABLE, "정책 삭제를 위해서는 상태가 HOLD 이어야 합니다.")
    ;

    private HttpStatus errorStatus;
    private String errorMessage;

    GradePointPolicyErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }
}
