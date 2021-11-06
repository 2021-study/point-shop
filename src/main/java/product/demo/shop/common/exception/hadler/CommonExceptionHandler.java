package product.demo.shop.common.exception.hadler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import product.demo.shop.common.exception.CommonErrorCode;
import product.demo.shop.common.exception.CommonErrorResponse;
import product.demo.shop.common.exception.CommonException;
import product.demo.shop.domain.auth.exception.PointShopAuthException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({CommonException.class, PointShopAuthException.class})
    @ResponseBody
    public ResponseEntity<CommonErrorResponse> handleCommonException(
            CommonException e, HttpServletRequest request) {
        return new ResponseEntity(CommonErrorResponse.createErrorResponse(e), e.getErrorStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CommonErrorResponse> handleException(
            Exception e, HttpServletRequest request) {
        request.getSession();
        var boxedException = new CommonException(CommonErrorCode.UNKNOWN_ERROR, e);
        return new ResponseEntity(
                CommonErrorResponse.createErrorResponse(boxedException),
                boxedException.getErrorStatus());
    }
}
