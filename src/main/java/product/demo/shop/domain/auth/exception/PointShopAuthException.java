package product.demo.shop.domain.auth.exception;

import product.demo.shop.common.exception.CommonException;
import product.demo.shop.common.exception.ErrorCode;

public class PointShopAuthException extends CommonException {

    public PointShopAuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PointShopAuthException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

    public PointShopAuthException(ErrorCode errorCode, String description) {
        super(errorCode, description);
    }
}
