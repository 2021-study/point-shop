package product.demo.shop.domain.grade.entity.enums;

import lombok.Getter;

@Getter
public enum GradePolicyType {
    INCREMENT("증가"),
    DECREMENT("할인")
    ;
    private final String value;

    GradePolicyType(String value) {
        this.value = value;
    }
}
