package product.demo.shop.domain.pointpolicy.exception;

import product.demo.shop.common.exception.CommonException;
import product.demo.shop.common.exception.ErrorCode;

public class GradePointPolicyException extends CommonException {
    public GradePointPolicyException(ErrorCode errorCode) {
        super(errorCode);
    }

    public GradePointPolicyException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

    public GradePointPolicyException(ErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public GradePointPolicyException(ErrorCode errorCode, String description, Throwable throwable) {
        super(errorCode, description, throwable);
    }
}
