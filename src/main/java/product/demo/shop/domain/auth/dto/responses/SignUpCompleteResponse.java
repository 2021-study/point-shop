package product.demo.shop.domain.auth.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import product.demo.shop.domain.user.entity.enums.UserStatusType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpCompleteResponse {
    private String userAccountId;
    private UserStatusType userStatus;
}
