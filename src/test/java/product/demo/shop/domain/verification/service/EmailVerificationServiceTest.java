package product.demo.shop.domain.verification.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import product.demo.shop.domain.verification.entity.EmailVerificationEntity;
import product.demo.shop.domain.verification.enums.VerificationCodeStatus;
import product.demo.shop.domain.verification.exception.EmailVerificationErrorCode;
import product.demo.shop.domain.verification.exception.EmailVerificationException;
import product.demo.shop.domain.verification.repository.EmailVerificationRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class EmailVerificationServiceTest {
    @InjectMocks
    EmailVerificationService emailVerificationService;

    @Mock
    EmailVerificationRepository emailVerificationRepository;
    final String VERIFICATION_CODE = "0afdf841-e2f7-4a70-8d75-1865332ec375";
    final long USER_ID = 1L;


    @Nested
    class 이메일_인증_코드을_통한_이메일_인증 {

        @Test
        void 해당_코드가_정상적이면_인증에_성공한다() {
            EmailVerificationEntity emailVerificationEntity = createEmailVerificationEntity(VERIFICATION_CODE, LocalDateTime.now().plus(3L, ChronoUnit.DAYS), USER_ID, VerificationCodeStatus.CREATED);
            given(emailVerificationRepository.findByVerificationCode(VERIFICATION_CODE)).willReturn(Optional.of(emailVerificationEntity));

            emailVerificationService.verifyEmailUsingVerificationCode(VERIFICATION_CODE);

            verify(emailVerificationRepository, times(1)).findByVerificationCode(VERIFICATION_CODE);
            assertThat(emailVerificationEntity.getVerificationCodeStatus(),is(equalTo(VerificationCodeStatus.CONFIRMED)));
        }

        @Test
        void 해당_코드가_존재하지_않으면_에러를_내보낸다() {
            given(emailVerificationRepository.findByVerificationCode(VERIFICATION_CODE)).willReturn(Optional.empty());

            EmailVerificationException emailVerificationException = assertThrows(EmailVerificationException.class, () -> {
                emailVerificationService.verifyEmailUsingVerificationCode(VERIFICATION_CODE);
            });
            assertThat(emailVerificationException.getErrorStatus(), is(equalTo(HttpStatus.BAD_REQUEST)));
            assertThat(emailVerificationException.getErrorMessage(), is(equalTo(EmailVerificationErrorCode.NOT_FOUND_TOKEN.getErrorMessage())));
        }

        @Test
        void 해당_코드가_유효기간이_지났으면_에러를_내보낸다() {
            EmailVerificationEntity emailVerificationEntity = createEmailVerificationEntity(VERIFICATION_CODE, LocalDateTime.now().minus(3L, ChronoUnit.DAYS), USER_ID, VerificationCodeStatus.CREATED);
            given(emailVerificationRepository.findByVerificationCode(VERIFICATION_CODE)).willReturn(Optional.of(emailVerificationEntity));

            EmailVerificationException emailVerificationException = assertThrows(EmailVerificationException.class, () -> {
                emailVerificationService.verifyEmailUsingVerificationCode(VERIFICATION_CODE);
            });
            assertThat(emailVerificationException.getErrorStatus(), is(equalTo(HttpStatus.BAD_REQUEST)));
            assertThat(emailVerificationException.getErrorMessage(), is(equalTo(EmailVerificationErrorCode.EXPIRED_TOKEN.getErrorMessage())));
        }

        @Test
        void 해당_코드가_이미_사용되었으면_에러를_내보낸다() {
            EmailVerificationEntity emailVerificationEntity = createEmailVerificationEntity(VERIFICATION_CODE, LocalDateTime.now().plus(3L, ChronoUnit.DAYS), USER_ID, VerificationCodeStatus.CONFIRMED);
            given(emailVerificationRepository.findByVerificationCode(VERIFICATION_CODE)).willReturn(Optional.of(emailVerificationEntity));

            EmailVerificationException emailVerificationException = assertThrows(EmailVerificationException.class, () -> {
                emailVerificationService.verifyEmailUsingVerificationCode(VERIFICATION_CODE);
            });
            assertThat(emailVerificationException.getErrorStatus(), is(equalTo(HttpStatus.BAD_REQUEST)));
            assertThat(emailVerificationException.getErrorMessage(), is(equalTo(EmailVerificationErrorCode.ALREADY_USED_TOKEN.getErrorMessage())));
        }
    }

    @Nested
    class 이메일_인증_코드_생성 {
        @Test
        void 이메일_인증_코드_생성에_성공한다() {
            String code = emailVerificationService.createVerificationCode(USER_ID);
            verify(emailVerificationRepository, times(1)).save(any(EmailVerificationEntity.class));
            //TODO 여기 테스트를 어떤식으로 해야할 지 모르겠음.
            assertThat(code.length(), is(equalTo(36)));
        }
    }

    private EmailVerificationEntity createEmailVerificationEntity(String verificationCode, LocalDateTime expiredDate, long userId, VerificationCodeStatus verificationCodeStatus) {
        return EmailVerificationEntity
                .builder()
                .emailVerificationCodeId(1L)
                .verificationCode(verificationCode)
                .expiredDate(expiredDate)
                .verificationCodeStatus(verificationCodeStatus)
                .userId(userId)
                .build();
    }

}