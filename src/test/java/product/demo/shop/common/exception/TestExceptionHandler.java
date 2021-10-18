package product.demo.shop.common.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class TestExceptionHandler {

    @ExceptionHandler(
            TestException.class
    )
    @ResponseBody
    public ResponseEntity<CommonErrorResponse> handleTestException(CommonException e, HttpServletRequest request) {
        return new ResponseEntity(CommonErrorResponse.createErrorResponse(e), e.getErrorStatus());
    }
}
