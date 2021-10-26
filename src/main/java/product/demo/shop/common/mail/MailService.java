package product.demo.shop.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    // Email 전송시 외부 SMTP 서버와 연동이 필요한데,
    // 이를 비동기로 처리하도록 하여 성능 향상 도모가 가능할 듯 하여
    // 메일 전송 기능을 Async로 구현하고자 합니다.
    @Async
    public void sendGoogleMail() {
        // TODO : Not yet Implement
    }
}
