package product.demo.shop.domain.pointpolicy.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyStatusType;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyType;
import product.demo.shop.domain.pointpolicy.entity.enums.MeasurementType;


import java.math.BigDecimal;

@Getter
@ToString
public class GradePolicyDto {

    private Long gradePolicyId;
    private Long userGradeId;
    private GradeName gradeName;
    private GradePolicyObject policyObject;
    private GradePolicyType policyType;
    private MeasurementType unitOfMeasure;
    private String policyName;
    private BigDecimal appliedValue;
    private GradePolicyStatusType policyStatus;

    @QueryProjection
    public GradePolicyDto(
            Long gradePolicyId,
            Long userGradeId,
            GradeName gradeName,
            GradePolicyObject policyObject,
            GradePolicyType policyType,
            MeasurementType unitOfMeasure,
            String policyName,
            BigDecimal appliedValue,
            GradePolicyStatusType policyStatus) {
        this.gradePolicyId = gradePolicyId;
        this.userGradeId = userGradeId;
        this.gradeName = gradeName;
        this.policyObject = policyObject;
        this.policyType = policyType;
        this.unitOfMeasure = unitOfMeasure;
        this.policyName = policyName;
        this.appliedValue = appliedValue;
        this.policyStatus = policyStatus;
    }
}
