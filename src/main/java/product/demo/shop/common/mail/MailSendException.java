package product.demo.shop.common.mail;

import product.demo.shop.common.exception.CommonException;
import product.demo.shop.common.exception.ErrorCode;

public class MailSendException extends CommonException {

    public MailSendException(ErrorCode errorCode,
            Throwable throwable) {
        super(errorCode, throwable);
    }
}
