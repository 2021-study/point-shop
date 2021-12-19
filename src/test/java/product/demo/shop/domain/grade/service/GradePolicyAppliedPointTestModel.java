package product.demo.shop.domain.grade.service;

import lombok.Getter;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyType;
import product.demo.shop.domain.pointpolicy.entity.enums.MeasurementType;

import java.math.BigDecimal;

@Getter
public class GradePolicyAppliedPointTestModel {

    private final GradePolicyType gradePolicyType;
    private final MeasurementType measurementType;
    private final BigDecimal midAppliedValue; // 중간 처리된

    private GradePolicyAppliedPointTestModel(
            GradePolicyType gradePolicyType,
            MeasurementType measurementType,
            BigDecimal midAppliedValue) {
        this.gradePolicyType = gradePolicyType;
        this.measurementType = measurementType;
        this.midAppliedValue = midAppliedValue;
    }

    public static GradePolicyAppliedPointTestModel of(
            GradePolicyType gradePolicyType,
            MeasurementType measurementType,
            BigDecimal midAppliedValue) {
        return new GradePolicyAppliedPointTestModel(
                gradePolicyType, measurementType, midAppliedValue);
    }
}
