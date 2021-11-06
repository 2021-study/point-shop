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

    private String status;

    public static SignupResponse of(String email, Long userInfoId, String status){
        return new SignupResponse(email, userInfoId, status);
    }
}
