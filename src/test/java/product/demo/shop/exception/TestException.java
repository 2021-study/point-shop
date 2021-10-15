package product.demo.shop.exception;

import product.demo.shop.common.exception.CommonException;
import product.demo.shop.common.exception.ErrorCode;

public class TestException extends CommonException {

    public TestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
