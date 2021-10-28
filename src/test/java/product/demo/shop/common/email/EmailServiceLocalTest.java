package product.demo.shop.common.email;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;
import product.demo.shop.common.mail.EmailParameter;
import product.demo.shop.common.mail.MailService;

@SpringBootTest
@ActiveProfiles("local")
@Disabled
public class EmailServiceLocalTest {

    @Autowired
    MailService mailService;

    @Test
    @DisplayName("실제 Google SMTP 서버와 연동하여 Mail 전송 테스트를 수행한다.")
    public void sendMailTest() throws InterruptedException {
        var emailParam = new EmailParameter("dlswp113@gmail.com",
                "테스트 메일전송입니다.",
                "테슷흐");

        mailService.sendGoogleMail(emailParam);
        Thread.sleep(3_000); // 서브 스레드에서 비동기로 돌고 있기 때문에 메인 테스트 스레드를 유지시켜주어야 한다.
    }

    @Test
    @DisplayName("비동기로 메일전송이 처리가 되어야 한다. (1번 처리에 2초가 걸리는 요청 5개가 병렬로 처리됨)")
    public void successSendMailAsync() throws InterruptedException {
        AtomicInteger index = new AtomicInteger();

        var mailList = Stream.generate(() -> new EmailParameter(
                        "dlswp113@gmail.com",
                        "테스트 메일전송입니다.",
                        "TEST"+index.incrementAndGet()))
                .limit(5)
                .collect(Collectors.toList());
        var stopwatch = new StopWatch();
        stopwatch.start();

        for(EmailParameter mail : mailList){
            this.mailService.sendGoogleMail(mail);
        }
        stopwatch.stop();
        assertThat(stopwatch.getTotalTimeSeconds()).isLessThan(1.0); // 1초 컷

        System.out.println(stopwatch.prettyPrint());

        Thread.sleep(10_000); // 서브 스레드에서 비동기로 돌고 있기 때문에 메인 테스트 스레드를 유지시켜주어야 한다.
    }
}
