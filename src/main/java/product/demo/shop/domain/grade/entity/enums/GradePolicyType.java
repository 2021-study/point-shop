package product.demo.shop.domain.grade.entity.enums;

import lombok.Getter;

@Getter
public enum GradePolicyType {
    INC("증가"),
    DEC("할인")
    ;
    private final String value;

    GradePolicyType(String value) {
        this.value = value;
    }
}
