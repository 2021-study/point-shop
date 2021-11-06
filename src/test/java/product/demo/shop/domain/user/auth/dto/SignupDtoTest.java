package product.demo.shop.domain.user.auth.dto;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.domain.auth.dto.SignupDto;
import product.demo.shop.domain.auth.dto.requests.SignupRequest;
import product.demo.shop.domain.user.entity.enums.UserStatusType;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
public class SignupDtoTest {

    @Test
    public void getterTest(){
        var dto = SignupDto.builder()
                .email("dlswp113@gmail.com")
                .userAccountId("jay")
                .name("hwnag")
                .password("123454")
                .phoneNumber("000000000")
                .address("사랑시고백구행복동")
                .userInfoId(1L)
                .emailVerificationCode("1213131")
                .userStatus(UserStatusType.REGISTERED_NOT_CONFIRMED)
                .emailVerificationUrl("http://localhost:8080/")
                .build();

            assertAll(
                    ()->assertNotNull(dto.getEmail()),
                    ()->assertNotNull(dto.getUserAccountId()),
                    ()->assertNotNull(dto.getName()),
                    ()->assertNotNull(dto.getPassword()),
                    ()->assertNotNull(dto.getPhoneNumber()),
                    ()->assertNotNull(dto.getAddress()),
                    ()->assertNotNull(dto.getUserInfoId()),
                    ()->assertNotNull(dto.getEmailVerificationCode()),
                    ()->assertNotNull(dto.getEmailVerificationUrl()),
                    ()->assertNotNull(dto.getUserStatus())
            );
    }

    @Test
    public void toSignupDtoTest(){
        var testRequest = SignupRequest.builder()
                .email("dlswp113@gmail.com")
                .name("hwang")
                .userAccountId("jay")
                .password("12345")
                .phoneNumber("000-0000-0000")
                .address("사랑시고백구행복동")
                .build();

        assertNotNull(SignupDto.toSignupDto(testRequest));
        assertAll(
                () -> assertEquals(testRequest.getEmail(), SignupDto.toSignupDto(testRequest).getEmail()),
                () -> assertEquals(testRequest.getAddress(), SignupDto.toSignupDto(testRequest).getAddress()),
                () -> assertEquals(testRequest.getUserAccountId(), SignupDto.toSignupDto(testRequest).getUserAccountId())
        );
    }
}
