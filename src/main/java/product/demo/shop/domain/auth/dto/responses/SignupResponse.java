package product.demo.shop.domain.auth.dto.responses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignupResponse {
    @Email
    @NotNull
    private String email;

    @NotNull
    private Long userInfoId;

    @NotNull
    private String verificationUrl;

    public static SignupResponse of(String email, Long userInfoId, String verificationUrl){
        return new SignupResponse(email, userInfoId, verificationUrl);
    }
}
