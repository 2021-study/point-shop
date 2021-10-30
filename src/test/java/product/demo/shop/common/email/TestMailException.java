package product.demo.shop.common.email;

import org.springframework.mail.MailException;

// MailException이 Abstract class 라서 테스트를 위해 임시로 만든 Exception 클래스
public class TestMailException extends MailException {
    public TestMailException(String msg) {
        super(msg);
    }

    public TestMailException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
