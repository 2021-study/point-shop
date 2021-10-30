package product.demo.shop.common.email;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import product.demo.shop.common.mail.EmailErrorCode;
import product.demo.shop.common.mail.EmailParameter;
import product.demo.shop.common.mail.MailSendException;
import product.demo.shop.common.mail.MailService;

public class EmailServiceTest {

    private MailService mailService;

    public EmailServiceTest() {
        this.mailService = new MailService(new MockMailSender(), "testsender@gmail.com");
    }

    @Test
    @DisplayName("메일 전송에 성공하면 예외가 발생해서는 안된다.")
    public void successMailTest() {
        var emailParam =
                EmailParameter.builder()
                        .receiverEmailAddress("dlswp113@gmail.com")
                        .title("테스트 메일 전송입니다.")
                        .content("테슷흐")
                        .build();

        assertDoesNotThrow(() -> this.mailService.sendGoogleMail(emailParam));
    }

    @Test
    @DisplayName("메일 전송시 예외 발생 시 전환된 예외가 발생하여야 한다.(EMAIL_SERVER_AUTH_FAIL)")
    public void authFailTest() {
        var emailParam =
                EmailParameter.builder()
                        .receiverEmailAddress("dlswp113@gmail.com")
                        .title("테스트 메일 전송입니다.")
                        .content("EMAIL_SERVER_AUTH_FAIL")
                        .build();

        var exception =
                assertThrows(
                        MailSendException.class, () -> this.mailService.sendGoogleMail(emailParam));

        assertThat(exception.getErrorCode())
                .isEqualTo(EmailErrorCode.EMAIL_SERVER_AUTH_FAIL.getErrorStatus().value());
    }

    @Test
    @DisplayName("메일 전송시 예외 발생 시 전환된 예외가 발생하여야 한다.(EMAIL_SEND_PROCESS_ERROR)")
    public void mailSendFailTest() {
        var emailParam =
                EmailParameter.builder()
                        .receiverEmailAddress("dlswp113@gmail.com")
                        .title("테스트 메일 전송입니다.")
                        .content("EMAIL_SEND_PROCESS_ERROR")
                        .build();

        var exception =
                assertThrows(
                        MailSendException.class, () -> this.mailService.sendGoogleMail(emailParam));

        assertThat(exception.getErrorCode())
                .isEqualTo(EmailErrorCode.EMAIL_SEND_PROCESS_ERROR.getErrorStatus().value());
    }

    @Test
    @DisplayName("메일 전송시 예외 발생 시 전환된 예외가 발생하여야 한다.(EMAIL_UNKNOWN_ERROR)")
    public void unknownServerErrorTest() {
        var emailParam =
                EmailParameter.builder()
                        .receiverEmailAddress("dlswp113@gmail.com")
                        .title("테스트 메일 전송입니다.")
                        .content("EMAIL_UNKNOWN_ERROR")
                        .build();

        var exception =
                assertThrows(
                        MailSendException.class, () -> this.mailService.sendGoogleMail(emailParam));

        assertThat(exception.getErrorCode())
                .isEqualTo(EmailErrorCode.EMAIL_UNKNOWN_ERROR.getErrorStatus().value());
    }
}
