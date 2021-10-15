package product.demo.shop.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CommonErrorResponse {

    int errorCode;
    String errorMessage;

    public static CommonErrorResponse createErrorResponse(CommonException commonException) {
        return new CommonErrorResponse(commonException.getErrorCode(), commonException.getErrorMessage());
    }
}
