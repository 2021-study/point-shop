package product.demo.shop.common.exception.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import product.demo.shop.common.exception.CommonErrorResponse;
import product.demo.shop.common.exception.CommonException;
import product.demo.shop.domain.auth.exception.PointShopAuthException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(
            {
                    CommonException.class,
                    PointShopAuthException.class
            }
    )
    @ResponseBody
    public ResponseEntity<CommonErrorResponse> handleTestException(CommonException e, HttpServletRequest request) {
        return new ResponseEntity(CommonErrorResponse.createErrorResponse(e), e.getErrorStatus());
    }
}
