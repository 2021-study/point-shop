package product.demo.shop.common.exception.hadler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import product.demo.shop.common.exception.CommonErrorCode;
import product.demo.shop.common.exception.CommonErrorResponse;
import product.demo.shop.common.exception.CommonException;
import product.demo.shop.auth.exception.PointShopAuthException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({CommonException.class, PointShopAuthException.class})
    @ResponseBody
    public ResponseEntity<CommonErrorResponse> handleCommonException(
            CommonException e, HttpServletRequest request) {
        return new ResponseEntity(CommonErrorResponse.createErrorResponse(e), e.getErrorStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<List<ObjectError>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        var exception = e;
        var fieldErrorList = exception.getBindingResult().getFieldErrors();

        var returnList =
                fieldErrorList.stream()
                        .map(
                                (it) ->
                                        "([Field] : "
                                                + it.getField()
                                                + "  [Value] : "
                                                + it.getRejectedValue()
                                                + "  [Message] : "
                                                + it.getDefaultMessage()
                                                + ")")
                        .collect(Collectors.toList());

        var boxedException = new CommonException(HttpStatus.BAD_REQUEST, returnList.toString(), e);
        return new ResponseEntity(
                CommonErrorResponse.createErrorResponse(boxedException),
                boxedException.getErrorStatus());
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
