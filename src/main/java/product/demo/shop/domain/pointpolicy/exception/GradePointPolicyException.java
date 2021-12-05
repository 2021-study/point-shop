package product.demo.shop.domain.pointpolicy.exception;

import product.demo.shop.common.exception.CommonException;
import product.demo.shop.common.exception.ErrorCode;

public class GradePointPolicyException extends CommonException {
    public GradePointPolicyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
