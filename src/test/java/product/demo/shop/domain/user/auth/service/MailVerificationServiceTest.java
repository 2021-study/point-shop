package product.demo.shop.domain.user.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.common.mail.MailService;
import product.demo.shop.domain.auth.dto.MailValidationDto;
import product.demo.shop.domain.auth.dto.requests.MailValidationRequest;
import product.demo.shop.domain.auth.exception.PointShopAuthErrorCode;
import product.demo.shop.domain.auth.exception.PointShopAuthException;
import product.demo.shop.domain.auth.service.MailValidationServiceImpl;
import product.demo.shop.domain.user.entity.EmailVerificationEntity;
import product.demo.shop.domain.user.repository.jpa.EmailVerificationRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class MailVerificationServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock private MailService mailService;

    @Mock private EmailVerificationRepository emailVerificationRepository;

    @InjectMocks private MailValidationServiceImpl mailValidationService;

    @Test
    @DisplayName("검증코드 Email 전송 로직 성공")
    public void makeMailValidation() throws Exception {
        var testRequest = MailValidationRequest.of("dlswp113@gmail.com", 1L);
        var testValidDto = MailValidationDto.makeValidationCodeFromMailValidRequest(testRequest);
        var testSendEmail = EmailVerificationEntity.fromMailValidationDto(testValidDto, 180);
        // given
        doNothing().when(mailService).sendMail(any());
        when(emailVerificationRepository.save(any())).thenReturn(testSendEmail);

        var result =
                assertDoesNotThrow(
                        () -> this.mailValidationService.makeMailValidation(testRequest));

        log.info(objectMapper.writeValueAsString(result));
        assertEquals("dlswp113@gmail.com", result.getEmail());
        assertNotNull(result.getTokenString());
    }

    @Test
    @DisplayName("메일 인증 완료 로직 성공")
    public void validateMailCode_success() throws Exception {
        when(emailVerificationRepository.findByUserIdAndVerificationCode(any(), any()))
                .thenReturn(
                    Optional.of(
                            EmailVerificationEntity.builder()
                                    .emailVerificationCodeId(1L)
                                    .verificationCode("string")
                                    .userId(1L)
                                    .expiredDate(LocalDateTime.now().plusYears(90000))
                                    .verificationCodeStatus("CREATED")
                                    .build()
                    )
                );

        when(emailVerificationRepository.save(any())).thenReturn(
                EmailVerificationEntity.builder()
                        .emailVerificationCodeId(1L)
                        .verificationCode("string")
                        .userId(1L)
                        .expiredDate(LocalDateTime.now())
                        .verificationCodeStatus("CONFIRMED")
                        .build()
        );

        assertDoesNotThrow(()->{
            var result = this.mailValidationService.validateMailCode(1L, "string");
            assertEquals("CONFIRMED", result.getValidationStatus());
        });
    }

    @Test
    @DisplayName("인증 코드 만료로 validateMailCode Failed")
    public void validateMailCode_expired() throws Exception {
        var expiredEmailCode =
                EmailVerificationEntity.builder()
                        .userId(1L)
                        .verificationCode("test-Strings")
                        .expiredDate(LocalDateTime.now().minusDays(1L))
                        .emailVerificationCodeId(1L)
                        .verificationCodeStatus("CREATED")
                        .build();

        when(emailVerificationRepository.findByUserIdAndVerificationCode(1L, "test-Strings"))
                .thenReturn(Optional.of(expiredEmailCode));

        var exception =
                assertThrows(
                        PointShopAuthException.class,
                        () -> this.mailValidationService.validateMailCode(1L, "test-Strings"));

        assertEquals(
                PointShopAuthErrorCode.MAIL_VERIFICATION_CODE_EXPIRED.getErrorMessage(),
                exception.getErrorMessage());
    }
}
