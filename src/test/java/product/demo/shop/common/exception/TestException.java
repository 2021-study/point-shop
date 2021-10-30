package product.demo.shop.common.exception;

public class TestException extends CommonException {

    public TestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TestException(ErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public TestException(ErrorCode errorCode, String description, Throwable throwable) {
        super(errorCode, description, throwable);
    }
}
