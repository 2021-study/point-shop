package product.demo.shop.domain.user.entity.enums;

import lombok.Getter;

@Getter
public enum UserStatusType {

    REGISTERED_NOT_CONFIRMED("메일 미인증 유저"),
    VERIFY_CODE_ISSUED("메일 코드 발송 완료"),
    VERIFIED("인증 완료 유저"),
    VERIFICATION_FAILED("인증 실패 된 유저")
    ;
    private final String value;

    UserStatusType(String value) {
        this.value = value;
    }
}
