package product.demo.shop.common.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import product.demo.shop.common.mail.EmailErrorCode;

@Slf4j
public class MockMailSender implements MailSender {

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        try {
            log.info("===========================");
            log.info("Processing Mail......");
            log.info("===========================");
            Thread.sleep(2_000); // send mail을 처리하는데 2초의 지연이 발생한다고 가정.

            log.info("Receiver Address : " + simpleMessage.getTo());
            log.info("From Address : " + simpleMessage.getFrom());
            log.info("Title : " + simpleMessage.getSubject());
            log.info("Content : " + simpleMessage.getText());

            // 예외 테스트를 위해 text에 특정 문자열이 오면 예외를 던지도록 함.
            if (simpleMessage.getText().equals(EmailErrorCode.EMAIL_SERVER_AUTH_FAIL.name())) {
                log.error("EMAIL_SERVER_AUTH_FAIL");
                throw new MailAuthenticationException(EmailErrorCode.EMAIL_SERVER_AUTH_FAIL.name());
            } else if (simpleMessage.getText().equals(EmailErrorCode.EMAIL_SEND_PROCESS_ERROR.name())) {
                log.error("EMAIL_SEND_PROCESS_ERROR");
                throw new TestMailException(EmailErrorCode.EMAIL_UNKNOWN_ERROR.name());
            } else if (simpleMessage.getText().equals("EMAIL_UNKNOWN_ERROR")) {
                log.error("RUNTIME_EXCEPTION");
                throw new RuntimeException(EmailErrorCode.EMAIL_UNKNOWN_ERROR.name());
            } else {
                // empty
                log.debug("no triggered Test Exception=====");
            }

            log.info("===========================");
            log.info("Mail Send Success......");
            log.info("===========================");

        } catch (InterruptedException e) {
            e.printStackTrace(); // 운영 소스에 printStackTrace 호출을 자제 합니다.
        } catch (Exception ex) {
            log.error("==========================");
            throw ex;
        }
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace(); // 운영 소스에 printStackTrace 호출을 자제 합니다.
        }
    }
}
