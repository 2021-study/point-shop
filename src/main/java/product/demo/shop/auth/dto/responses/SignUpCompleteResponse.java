package product.demo.shop.auth.dto.responses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import product.demo.shop.domain.user.entity.enums.UserStatusType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpCompleteResponse {
    private String userAccountId;
    private UserStatusType userStatus;

    public static SignUpCompleteResponse of(String userAccountId, UserStatusType userStatus) {
        return new SignUpCompleteResponse(userAccountId, userStatus);
    }
}
