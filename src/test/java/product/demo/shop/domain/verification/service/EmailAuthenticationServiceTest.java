package product.demo.shop.domain.verification.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import product.demo.shop.domain.verification.exception.EmailVerificationErrorCode;
import product.demo.shop.domain.verification.exception.EmailVerificationException;
import product.demo.shop.domain.verification.repository.EmailAuthenticationRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class EmailAuthenticationServiceTest {
    @InjectMocks
    EmailAuthenticationService emailAuthenticationService;

    @Mock
    EmailAuthenticationRepository emailAuthenticationRepository;
    final String VERIFICATION_CODE = "0afdf841-e2f7-4a70-8d75-1865332ec375";
    final long USER_SEQ = 1L;


    @Nested
    class 이메일_인증_코드을_통한_이메일_인증 {

        @Test
        void 해당_코드가_정상적이면_인증에_성공한다() {
            emailAuthenticationService.verifyEmailUsingVerificationCode(VERIFICATION_CODE);
            verify(emailAuthenticationRepository, times(1)).findByVerificationCode(VERIFICATION_CODE);
        }

        @Test
        void 해당_코드가_존재하지_않으면_에러를_내보낸다() {
            EmailVerificationException emailVerificationException = assertThrows(EmailVerificationException.class, () -> {
                emailAuthenticationService.verifyEmailUsingVerificationCode(VERIFICATION_CODE);
            });
            assertThat(emailVerificationException.getErrorStatus(), is(equalTo(HttpStatus.BAD_REQUEST)));
            assertThat(emailVerificationException.getMessage(), is(equalTo(EmailVerificationErrorCode.NOT_FOUND_TOKEN.getErrorMessage())));


        }

        @Test
        void 해당_코드가_유효기간이_지났으면_에러를_내보낸다() {
            EmailVerificationException emailVerificationException = assertThrows(EmailVerificationException.class, () -> {
                emailAuthenticationService.verifyEmailUsingVerificationCode(VERIFICATION_CODE);
            });
            assertThat(emailVerificationException.getErrorStatus(), is(equalTo(HttpStatus.BAD_REQUEST)));
            assertThat(emailVerificationException.getMessage(), is(equalTo(EmailVerificationErrorCode.EXPIRED_TOKEN.getErrorMessage())));
        }

        @Test
        void 해당_코드가_이미_사용되었으면_에러를_내보낸다() {
            emailAuthenticationService.verifyEmailUsingVerificationCode(VERIFICATION_CODE);
            EmailVerificationException emailVerificationException = assertThrows(EmailVerificationException.class, () -> {
                emailAuthenticationService.verifyEmailUsingVerificationCode(VERIFICATION_CODE);
            });
            assertThat(emailVerificationException.getErrorStatus(), is(equalTo(HttpStatus.BAD_REQUEST)));
            assertThat(emailVerificationException.getMessage(), is(equalTo(EmailVerificationErrorCode.ALREADY_USED_TOKEN.getErrorMessage())));
        }
    }

    @Nested
    class 이메일_인증_코드_생성 {
        @Test
        void 이메일_인증_코드_생성에_성공한다() {
            String code = emailAuthenticationService.createVerificationCode(USER_SEQ);
            assertThat(code, is(equalTo(VERIFICATION_CODE)));
        }
    }
}