package product.demo.shop.auth.exception;

import product.demo.shop.common.exception.CommonException;
import product.demo.shop.common.exception.ErrorCode;

public class JwtValidationException extends CommonException {

    public JwtValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
