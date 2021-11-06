package product.demo.shop.domain.user.auth.dto;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.domain.auth.dto.requests.SignupRequest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class SignupRequestTest {

    @Test
    public void validatorTest_success(){

        var result = assertDoesNotThrow(()-> SignupRequest.builder()
                .email("dlswp113@gmail.com")
                .userAccountId("jay")
                .name("hwnag")
                .password("123454")
                .phoneNumber("000000000")
                .address("사랑시고백구행복동")
                .build()
        );

        assertEquals("dlswp113@gmail.com", result.getEmail());
    }
//
//    @Test
//    public void validatorTest_failedEmail() {
//        assertThrows(Exception.class,()-> SignupRequest.builder()
//                .email("dlswp113")
//                .userAccountId("jay")
//                .name("hwnag")
//                .password("123454")
//                .phoneNumber("000000000")
//                .address("사랑시고백구행복동")
//                .build()
//        );
//    }
//
//    @Test
//    public void validatorTest_failedNotNullProps(){
//        assertThrows(Exception.class,()-> {
//            var request = SignupRequest.builder()
//                            .email("dlswp113@gmail.com")
//                            .userAccountId("jay")
//                            .name("hwnag")
//                            .password(null)
//                            .phoneNumber("000000000")
//                            .address("사랑시고백구행복동")
//                            .build();
//                request.
//                }
//        );
//    }
}
