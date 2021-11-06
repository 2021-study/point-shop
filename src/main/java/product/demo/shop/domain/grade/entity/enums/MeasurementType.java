package product.demo.shop.domain.grade.entity.enums;

import lombok.Getter;

@Getter
public enum MeasurementType {
    PERCENT("백분율(%)"),
    POINT("포인트(정액)")
    ;

    private final String value;

    MeasurementType(String value) {
        this.value = value;
    }
}
