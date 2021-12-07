package product.demo.shop.auth.dto;

import lombok.Builder;
import lombok.Getter;
import product.demo.shop.auth.dto.requests.SignupRequest;
import product.demo.shop.domain.user.entity.enums.UserStatusType;

@Getter
@Builder
public class SignupDto {

    private String email;

    private String userAccountId;

    private String name;

    private String password;

    private String phoneNumber;

    private String address;

    private Long userInfoId;

    private String emailVerificationCode;

    private UserStatusType userStatus;

    private String emailVerificationUrl;

    public static SignupDto toSignupDto(SignupRequest request) {
        return SignupDto.builder()
                .email(request.getEmail())
                .userAccountId(request.getUserAccountId())
                .name(request.getName())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();
    }
}
