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
}
