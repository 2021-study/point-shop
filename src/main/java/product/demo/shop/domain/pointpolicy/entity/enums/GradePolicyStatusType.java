package product.demo.shop.domain.pointpolicy.entity.enums;

public enum GradePolicyStatusType {
    ACTIVATE("활성화"),
    HOLD("대기/보류"),
    CLOSED("비활성화")
    ;
    private final String value;

    GradePolicyStatusType(String value) {
        this.value = value;
    }

}
