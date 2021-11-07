package product.demo.shop.domain.pointpolicy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyStatusType;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyType;
import product.demo.shop.domain.pointpolicy.entity.enums.MeasurementType;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class GradePointPolicyManagementDto {
    private GradeName gradeName;
    private Long gradePolicyId;
    private Long userGradeId;
    private GradePolicyObject policyObject;
    private GradePolicyType policyType;
    private MeasurementType unitOfMeasure;
    private String policyName;
    private BigDecimal appliedValue;
    private GradePolicyStatusType policyStatus;

    public static GradePointPolicyManagementDto of(
            GradeName gradeName,
            Long gradePolicyId,
            Long userGradeId,
            GradePolicyObject policyObject,
            GradePolicyType policyType,
            MeasurementType unitOfMeasure,
            String policyName,
            BigDecimal appliedValue,
            GradePolicyStatusType policyStatus
    ){
       return new GradePointPolicyManagementDto(
               gradeName,
               gradePolicyId,
               userGradeId,
               policyObject,
               policyType,
               unitOfMeasure,
               policyName,
               appliedValue,
               policyStatus
       );
    }

    public static GradePointPolicyManagementDto of(
            Long gradePolicyId,
            Long userGradeId,
            GradePolicyObject policyObject,
            GradePolicyType policyType,
            MeasurementType unitOfMeasure,
            String policyName,
            BigDecimal appliedValue,
            GradePolicyStatusType policyStatus
    ) {
        return GradePointPolicyManagementDto.of(
                null,
                gradePolicyId,
                userGradeId,
                policyObject,
                policyType,
                unitOfMeasure,
                policyName,
                appliedValue,
                policyStatus
        );
    }

    public static GradePointPolicyManagementDto fromRequest(
        GradePointPolicyManagementRequest request
    ){
       return GradePointPolicyManagementDto.of(
               request.getGradeName(),
               null,
               request.getUserGradeId(),
               request.getPolicyObject(),
               request.getPolicyType(),
               request.getUnitOfMeasure(),
               request.getPolicyName(),
               request.getAppliedValue(),
               request.getPolicyStatus()
       );
    }
}
