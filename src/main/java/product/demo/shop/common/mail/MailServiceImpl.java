package product.demo.shop.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private final MailSender mailSender;

    private final String senderEmailAddress;

    public MailServiceImpl(
            MailSender mailSender, @Value("${spring.mail.username}") String senderEmailAddress) {
        this.mailSender = mailSender;
        this.senderEmailAddress = senderEmailAddress;
    }

    // Email 전송시 외부 SMTP 서버와 연동이 필요한데,
    // 이를 비동기로 처리하도록 하여 성능 향상 도모가 가능할 듯 하여
    // 메일 전송 기능을 Async로 구현하고자 합니다.
    // 이 호출이 정상적으로 종료가 되었는지 실패했는지에 대해 메인 호출 스레드에 전달하는 처리는 따로 없습니다.
    @Async
    public void sendMail(EmailParameter emailParameter) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(emailParameter.getReceiverEmailAddress());
        mailMessage.setFrom(senderEmailAddress);
        mailMessage.setSubject(emailParameter.getTitle());
        mailMessage.setText(emailParameter.getContent());

        try {
            this.mailSender.send(mailMessage);
        } catch (MailAuthenticationException ex) {
            throw new MailSendException(EmailErrorCode.EMAIL_SERVER_AUTH_FAIL, ex);
        } catch (MailException ex) {
            throw new MailSendException(EmailErrorCode.EMAIL_SEND_PROCESS_ERROR, ex);
        } catch (RuntimeException ex) {
            throw new MailSendException(EmailErrorCode.EMAIL_UNKNOWN_ERROR, ex);
        }
    }
}
