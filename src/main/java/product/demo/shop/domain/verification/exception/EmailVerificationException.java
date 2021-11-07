package product.demo.shop.domain.verification.exception;

import product.demo.shop.common.exception.CommonException;
import product.demo.shop.common.exception.ErrorCode;

public class EmailVerificationException extends CommonException {
    public EmailVerificationException(ErrorCode errorCode,
                             Throwable throwable) {
        super(errorCode, throwable);
    }

    public EmailVerificationException(ErrorCode errorCode) {
        super(errorCode);
    }
}