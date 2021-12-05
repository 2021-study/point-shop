package product.demo.shop.common.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception-test")
public class ExceptionTestController {

    @GetMapping("/unknown-error")
    public String unknownErrorTest() {
        throw new TestException(CommonErrorCode.UNKNOWN_ERROR);
    }

    @GetMapping("/unknown-error-with-description")
    public String unknownErrorWithDescription() {
        throw new TestException(
                CommonErrorCode.UNKNOWN_ERROR,
                "ErrorCode 이넘과 Description을 추가해서 예외를 던지고 싶다면 이렇게 사용하시면 됩니다.");
    }

    @GetMapping("/unknown-error-with-description-throwable")
    public String unknownErrorWithDescriptionAndThrowable() {
        throw new TestException(
                CommonErrorCode.UNKNOWN_ERROR,
                "ErrorCode 이넘과 Description을 추가하고," +
                        "Cause(원천 예외)를 같이 박싱해서 예외를 던지고 싶다면 이렇게 사용하시면 됩니다.",
                new Exception("This is Cause"));
    }
}
