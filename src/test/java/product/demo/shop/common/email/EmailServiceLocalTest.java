package product.demo.shop.common.email;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.concurrent.atomic.AtomicInteger;
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
import product.demo.shop.common.mail.MailServiceImpl;

@SpringBootTest
@ActiveProfiles("local")
@Disabled // 이 테스트는 test scope에서 테스트 되지 않습니다.
public class EmailServiceLocalTest {

    @Autowired
    MailService mailService;

    @Test
    @DisplayName("실제 Google SMTP 서버와 연동하여 Mail 전송 테스트를 수행한다.")
    public void sendMailTest() throws InterruptedException {
        var emailParam =
                EmailParameter.builder()
                        .receiverEmailAddress("dlswp113@gmail.com")
                        .title("테스트 메일 전송입니다.")
                        .content("테슷흐")
                        .build();

        mailService.sendMail(emailParam);
        Thread.sleep(3_000); // 서브 스레드에서 비동기로 돌고 있기 때문에 메인 테스트 스레드를 유지시켜주어야 한다.
    }

    @Test
    @DisplayName("비동기로 메일전송이 처리가 되어야 한다. (1번 처리에 2초가 걸리는 요청 5개가 병렬로 처리됨)")
    public void successSendMailAsync() throws InterruptedException {
        AtomicInteger index = new AtomicInteger();

        var stopwatch = new StopWatch();
        stopwatch.start(); //   Stopwatch 시작
        Stream.generate(
                        () ->
                                EmailParameter.builder()
                                        .receiverEmailAddress("dlswp113@gmail.com")
                                        .title("테스트 메일 전송입니다. - " + index.incrementAndGet())
                                        .content("테슷흐")
                                        .build())
                .limit(5)
                .forEach((it) -> this.mailService.sendMail(it));

        stopwatch.stop(); // Stopwatch 종료
        assertThat(stopwatch.getTotalTimeSeconds())
                .isLessThan(1.0); // 1초 컷 -> 실제로 메인은 1초내외로 종료가 되어야 테스트가 정상으로 볼 수 있다.

        System.out.println(stopwatch.prettyPrint());

        // 서브 스레드에서 비동기로 돌고 있기 때문에 메인 테스트 스레드를 유지시켜주어야 한다.
        // 경우에 따라서 10초가 지나도 모든 메일이 전송되지 않을 수 도 있습니다.
        Thread.sleep(10_000);
    }
}
