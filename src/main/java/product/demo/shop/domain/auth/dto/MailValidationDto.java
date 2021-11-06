package product.demo.shop.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import product.demo.shop.domain.auth.dto.requests.MailValidationRequest;
import product.demo.shop.domain.auth.dto.responses.SignupResponse;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Builder
public class MailValidationDto {
    private String email;
    private String tokenString;
    private Long userInfoId;
    private String mailValidationUrl;
    private Long emailVerificationEntityId;
    private String validationStatus;


    public static MailValidationDto fromMailValidRequest(MailValidationRequest mailValidationRequest){
        var dateBytes =
                mailValidationRequest
                        .getAppliedAt()
                        .toString()
                        .concat(mailValidationRequest.getUserInfoId().toString())
                        .getBytes(StandardCharsets.UTF_8);

        String tokenString = UUID.nameUUIDFromBytes(dateBytes).toString();

        String validUrl = "http://localhost:8077/api/v1/auth/verify/"+mailValidationRequest.getUserInfoId()+"/"+tokenString;

        return MailValidationDto.builder()
                .email(mailValidationRequest.getEmail())
                .tokenString(tokenString)
                .userInfoId(mailValidationRequest.getUserInfoId())
                .mailValidationUrl(validUrl)
                .build();
    }

    public void setEmailVerificationEntityId(Long emailVerificationEntityId){
        this.emailVerificationEntityId = emailVerificationEntityId;
    }

    public SignupResponse toSignupResponse(String status) {
        return SignupResponse.of(this.email, this.userInfoId, status);
    }
}
