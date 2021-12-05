package product.demo.shop.domain.pointpolicy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyStatusType;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyType;
import product.demo.shop.domain.pointpolicy.entity.enums.MeasurementType;


import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GradePointPolicyManagementRequest {

    private Long gradePolicyId;
    private GradeName gradeName;
    private Long userGradeId;
    private GradePolicyObject policyObject;
    private GradePolicyType policyType;
    private MeasurementType unitOfMeasure;
    private String policyName;
    private BigDecimal appliedValue;
    private GradePolicyStatusType policyStatus;

    public Long setGradePolicyIdIfNull(Long gradePolicyId){
        if(gradePolicyId!=null && gradePolicyId >0L){
            return this.gradePolicyId;
        }

        this.gradePolicyId = gradePolicyId;
        return this.gradePolicyId;
    }
}
