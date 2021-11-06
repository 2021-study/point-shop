package product.demo.shop.domain.auth.dto.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@JsonDeserialize(builder = SignupRequest.SignupRequestBuilder.class)
@Getter
@Builder
public class SignupRequest {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String userAccountId;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String address;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SignupRequestBuilder{

    }
}
