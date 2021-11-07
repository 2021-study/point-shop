package product.demo.shop.domain.pointpolicy.entity;

import lombok.*;
import product.demo.shop.common.entity.AuditEntity;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementDto;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementRequest;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyStatusType;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyType;
import product.demo.shop.domain.pointpolicy.entity.enums.MeasurementType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_GRADE_POLICY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class GradePolicyEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradePolicyId;

    @Column
    private long userGradeId;

    @Column
    @Enumerated(EnumType.STRING)
    private GradePolicyObject policyObject;

    @Column
    @Enumerated(EnumType.STRING)
    private GradePolicyType policyType;

    @Column
    @Enumerated(EnumType.STRING)
    private MeasurementType unitOfMeasure;

    @Column
    private BigDecimal appliedValue;

    @Column
    private String policyName;

    @Column
    @Enumerated(EnumType.STRING)
    private GradePolicyStatusType policyStatus;

    public GradePointPolicyManagementDto toManagementDto(){
        return GradePointPolicyManagementDto.of(
                this.gradePolicyId,
                this.userGradeId,
                this.policyObject,
                this.policyType,
                this.unitOfMeasure,
                this.policyName,
                this.appliedValue,
                this.policyStatus
        );
    }

    public static GradePolicyEntity fromManagementRequestForCreate(GradePointPolicyManagementRequest request){
        return GradePolicyEntity.builder()
                .policyName(request.getPolicyName())
                .appliedValue(request.getAppliedValue())
                .unitOfMeasure(request.getUnitOfMeasure())
                .policyObject(request.getPolicyObject())
                .policyStatus(request.getPolicyStatus())
                .policyType(request.getPolicyType())
                .build();
    }

    public static GradePolicyEntity fromManagementRequestForUpdate(GradePointPolicyManagementRequest request){
        return GradePolicyEntity.builder()
                .gradePolicyId(request.getGradePolicyId())
                .policyName(request.getPolicyName())
                .appliedValue(request.getAppliedValue())
                .unitOfMeasure(request.getUnitOfMeasure())
                .policyObject(request.getPolicyObject())
                .policyStatus(request.getPolicyStatus())
                .policyType(request.getPolicyType())
                .build();
    }
}
