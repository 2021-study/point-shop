package product.demo.shop.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.auth.dto.MailValidationDto;
import product.demo.shop.auth.dto.SignupDto;
import product.demo.shop.auth.dto.requests.MailValidationRequest;
import product.demo.shop.auth.exception.PointShopAuthErrorCode;
import product.demo.shop.auth.exception.PointShopAuthException;
import product.demo.shop.domain.grade.entity.UserGradeEntity;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.grade.repository.UserGradeRepository;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.entity.enums.UserStatusType;
import product.demo.shop.domain.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class AuthServiceImplTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock PasswordEncoder passwordEncoder;

    @Mock private UserRepository userRepository;

    @Mock private UserGradeRepository userGradeRepository;

    @Mock private MailValidationService mailValidationService;

    @InjectMocks private AuthServiceImpl authService;

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ??????")
    public void newUserSignUp_success() {
        var testInputSignupDto =
                SignupDto.builder()
                        .email("dlswp113@gmail.com")
                        .userAccountId("jay")
                        .address("???????????????????????????")
                        .password("1234")
                        .phoneNumber("010-0000-000")
                        .name("hwang")
                        .build();

        var sampleUserEntity =
                UserEntity.fromUserDtoWithStandAlone(
                        testInputSignupDto,
                        UserGradeEntity.builder()
                                .userGradeId(1L)
                                .gradeName(GradeName.BRONZE)
                                .build(),
                        new BCryptPasswordEncoder());

        var testOutputMailValidationDto =
                MailValidationDto.makeValidationCodeFromMailValidRequest(
                        MailValidationRequest.of("dlswp113@gmail.com", 1L));
        testOutputMailValidationDto.setEmailVerificationEntityId(2L);

        // given
        when(userRepository.existsByUserAccountId(any())).thenReturn(false);
        when(userGradeRepository.findByGradeName(GradeName.BRONZE))
                .thenReturn(
                        Optional.of(
                                UserGradeEntity.builder()
                                        .userGradeId(1L)
                                        .gradeName(GradeName.BRONZE)
                                        .build()));

        when(userRepository.save(any())).thenReturn(sampleUserEntity);

        when(mailValidationService.makeMailValidation(any()))
                .thenReturn(testOutputMailValidationDto);

        when(passwordEncoder.encode(any())).thenReturn("????????????");

        var testResult =
                assertDoesNotThrow(() -> this.authService.newUserSignUp(testInputSignupDto));

        assertNotNull(testResult);
        assertNotNull(testResult.getTokenString());
        assertEquals(2L, testResult.getEmailVerificationEntityId());
    }

    @Test
    @DisplayName("?????? ????????? ????????? ?????? ?????? ??????")
    public void newUserSignUp_duplicated() {
        var testInputSignupDto =
                SignupDto.builder()
                        .email("dlswp113@gmail.com")
                        .userAccountId("jay")
                        .address("???????????????????????????")
                        .password("1234")
                        .phoneNumber("010-0000-000")
                        .name("hwang")
                        .build();

        // given
        when(userRepository.existsByUserAccountId(any())).thenReturn(true);

        var resultException =
                assertThrows(
                        PointShopAuthException.class,
                        () -> this.authService.newUserSignUp(testInputSignupDto));

        log.info(resultException.toString());
        assertEquals(HttpStatus.BAD_REQUEST, resultException.getErrorStatus());
        assertEquals(
                PointShopAuthErrorCode.EXIST_USER.getErrorMessage(),
                resultException.getErrorMessage());
    }

    @Test
    @DisplayName("????????? ?????? ????????? ?????? ?????? ?????? ????????????(inhouse signup)??? ????????????.")
    public void completeSignUp_success() throws Exception {
        var testInputSignupDto =
                SignupDto.builder()
                        .email("dlswp113@gmail.com")
                        .userAccountId("jay")
                        .address("???????????????????????????")
                        .password("1234")
                        .phoneNumber("010-0000-000")
                        .name("hwang")
                        .build();

        var testResultMailDto =
                MailValidationDto.builder()
                        .emailVerificationEntityId(1L)
                        .userInfoId(1L)
                        .validationStatus("CONFIRMED")
                        .build();

        var sampleUserEntity =
                UserEntity.fromUserDtoWithStandAlone(
                        testInputSignupDto,
                        UserGradeEntity.builder()
                                .userGradeId(1L)
                                .gradeName(GradeName.BRONZE)
                                .build(),
                        new BCryptPasswordEncoder());

        when(mailValidationService.validateMailCode(any(), any())).thenReturn(testResultMailDto);

        when(userRepository.findById(any())).thenReturn(Optional.of(sampleUserEntity));

        var signupCompleteResult =
                assertDoesNotThrow(() -> this.authService.completeSignUp(1L, "string"));

        log.info(objectMapper.writeValueAsString(signupCompleteResult));
        assertEquals(UserStatusType.VERIFIED, signupCompleteResult.getUserStatus());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ??? RuntimeException??? ?????? ??? ?????? ?????? ?????? ??????")
    public void signupCompleteErrorTest() {
        var testResultMailDto =
                MailValidationDto.builder()
                        .emailVerificationEntityId(1L)
                        .userInfoId(1L)
                        .validationStatus("CONFIRMED")
                        .build();

        when(mailValidationService.validateMailCode(any(), any())).thenReturn(testResultMailDto);

        when(userRepository.findById(any())).thenThrow(new NoSuchElementException());

        var exception =
                assertThrows(PointShopAuthException.class,() -> this.authService.completeSignUp(1L, "string"));

        assertEquals(PointShopAuthErrorCode.SIGNUP_COMPLETE_ERROR.getErrorMessage(), exception.getErrorMessage());
    }

    @Test
    @DisplayName("???????????? PointShopAuthException??? ?????? ??? ?????? ?????? ????????? ????????? ??????.")
    public void internalPointShopAuthExceptionTest() {
        var testResultMailDto =
                MailValidationDto.builder()
                        .emailVerificationEntityId(1L)
                        .userInfoId(1L)
                        .validationStatus("CODE_CREATED_BUT_NOT_SENT") //TODO: ????????? ???????????? ???????????? ????????????,  ?????? ???????????? ?????? ?????? ??????, ?????? ?????? ????????? ??????.
                        .build();

        when(mailValidationService.validateMailCode(any(), any())).thenReturn(testResultMailDto);


        var exception =
                assertThrows(PointShopAuthException.class,() -> this.authService.completeSignUp(1L, "string"));

        assertEquals(PointShopAuthErrorCode.MAIL_VERIFICATION_ERROR.getErrorMessage(), exception.getErrorMessage());
    }
}
