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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.domain.auth.dto.MailValidationDto;
import product.demo.shop.domain.auth.dto.SignupDto;
import product.demo.shop.domain.auth.dto.requests.MailValidationRequest;
import product.demo.shop.domain.auth.exception.PointShopAuthErrorCode;
import product.demo.shop.domain.auth.exception.PointShopAuthException;
import product.demo.shop.domain.auth.service.AuthServiceImpl;
import product.demo.shop.domain.auth.service.MailValidationService;
import product.demo.shop.domain.grade.entity.UserGradeEntity;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.grade.repository.UserGradeRepository;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.entity.enums.UserStatusType;
import product.demo.shop.domain.user.repository.UserRepository;

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

    @Autowired
    @Mock private UserRepository userRepository;

    @Autowired
    @Mock private UserGradeRepository userGradeRepository;

    @Autowired
    @Mock private MailValidationService mailValidationService;

    @Autowired
    @InjectMocks private AuthServiceImpl authService;

    @Test
    @DisplayName("신규 유저 등록 성공 처리")
    public void newUserSignUp_success() {
        var testInputSignupDto = SignupDto.builder()
                .email("dlswp113@gmail.com")
                .userAccountId("jay")
                .address("사랑시고백구행복동")
                .password("1234")
                .phoneNumber("010-0000-000")
                .name("hwang")
                .build();

        var sampleUserEntity = UserEntity.fromUserDtoWithStandAlone(testInputSignupDto,UserGradeEntity.builder()
                .userGradeId(1L)
                .gradeName(GradeName.BRONZE)
                .build()
            , new BCryptPasswordEncoder()
        );

        var testOutputMailValidationDto = MailValidationDto.fromMailValidRequest(MailValidationRequest.of(
                "dlswp113@gmail.com",
                1L
        ));
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

        when(mailValidationService.makeMailValidation(any())).thenReturn(
                testOutputMailValidationDto
        );

        var testResult =
                assertDoesNotThrow(()->this.authService.newUserSignUp(testInputSignupDto));

        assertNotNull(testResult);
        assertNotNull(testResult.getTokenString());
        assertEquals(2L, testResult.getEmailVerificationEntityId());
    }

    @Test
    @DisplayName("기존 등록된 유저로 신규 등록 차단")
    public void newUserSignUp_duplicated(){
        var testInputSignupDto = SignupDto.builder()
                .email("dlswp113@gmail.com")
                .userAccountId("jay")
                .address("사랑시고백구행복동")
                .password("1234")
                .phoneNumber("010-0000-000")
                .name("hwang")
                .build();

        // given
        when(userRepository.existsByUserAccountId(any())).thenReturn(true);

        var resultException =
                assertThrows(PointShopAuthException.class,()->this.authService.newUserSignUp(testInputSignupDto));

        log.info(resultException.toString());
        assertEquals(HttpStatus.BAD_REQUEST, resultException.getErrorStatus());
        assertEquals(PointShopAuthErrorCode.EXIST_USER.getErrorMessage(), resultException.getErrorMessage());
    }

    @Test
    @DisplayName("이메일 인증 코드를 검증하여 자체 회원가입(inhouse signup)을 완료한다.")
    public void completeSignUp_success() throws Exception{
        var testInputSignupDto = SignupDto.builder()
                .email("dlswp113@gmail.com")
                .userAccountId("jay")
                .address("사랑시고백구행복동")
                .password("1234")
                .phoneNumber("010-0000-000")
                .name("hwang")
                .build();

        var testResultMailDto = MailValidationDto.builder()
                .emailVerificationEntityId(1L)
                .userInfoId(1L)
                .validationStatus("CONFIRMED")
                .build();

        var sampleUserEntity = UserEntity.fromUserDtoWithStandAlone(testInputSignupDto,UserGradeEntity.builder()
                .userGradeId(1L)
                .gradeName(GradeName.BRONZE)
                .build()
        ,new BCryptPasswordEncoder());

        when(mailValidationService.validateMailCode(any(), any())).thenReturn(
                testResultMailDto
        );

        when(userRepository.findById(any())).thenReturn(Optional.of(
                sampleUserEntity
        ));

        var signupCompleteResult =
                assertDoesNotThrow(()->this.authService.completeSignUp(1L, "string"));

        log.info(objectMapper.writeValueAsString(signupCompleteResult));
        assertEquals(UserStatusType.VERIFIED ,signupCompleteResult.getUserStatus());
    }

}
