package product.demo.shop.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonErrorResponse {

    int errorCode;
    String errorMessage;

    public static CommonErrorResponse createErrorResponse(CommonException commonException) {
        return new CommonErrorResponse(commonException.getErrorCode(), commonException.getErrorMessage());
    }
}
