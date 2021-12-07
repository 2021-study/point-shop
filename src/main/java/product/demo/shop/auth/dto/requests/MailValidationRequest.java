package product.demo.shop.auth.dto.requests;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailValidationRequest {

    private String email;
    private Long userInfoId;
    private LocalDateTime appliedAt;

    public static MailValidationRequest of(String email, Long userInfoId, LocalDateTime appliedAt) {
        return new MailValidationRequest(email, userInfoId, appliedAt);
    }

    public static MailValidationRequest of(String email, Long userInfoId) {
        return MailValidationRequest.of(email, userInfoId, LocalDateTime.now());
    }
}
