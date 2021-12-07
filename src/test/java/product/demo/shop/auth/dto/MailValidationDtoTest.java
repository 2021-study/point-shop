package product.demo.shop.auth.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.auth.dto.requests.MailValidationRequest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("test")
@Slf4j
public class MailValidationDtoTest {

    private final MailValidationRequest testMailValidationRequest =
            MailValidationRequest.of("dlswp113@gmail.com", 1L);

    private ObjectMapper testObjectMapper = new ObjectMapper();

    @Test
    @DisplayName("메일 인증코드 생성 정상 처리 확인 테스트")
    public void fromMailValidRequestTest_Success() throws Exception {
        var result =
                assertDoesNotThrow(
                        () -> MailValidationDto.makeValidationCodeFromMailValidRequest(testMailValidationRequest));

        log.info(">>>> " + testObjectMapper.writeValueAsString(result));
        assertNotNull(result.getMailValidationUrl());
        assertNotNull(result.getTokenString());

        // 아직 Save 전이므로 아래 속성들은 NULL 이어야 한다.
        assertNull(result.getValidationStatus());
        assertNull(result.getEmailVerificationEntityId());
    }

    @Test
    @DisplayName("SignupResponse 변환 테스트")
    public void toSignupResponseTest() {
        var mailValidationDto =
                assertDoesNotThrow(
                        () -> MailValidationDto.makeValidationCodeFromMailValidRequest(testMailValidationRequest));

        var result = mailValidationDto.toSignupResponse("SUCCESS");
        assertNotNull(result.getEmail());
        assertNotNull(result.getUserInfoId());
        assertNotNull(result.getStatus());
    }
}
