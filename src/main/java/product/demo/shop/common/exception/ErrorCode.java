package product.demo.shop.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getErrorStatus();
    String getErrorMessage();
}
