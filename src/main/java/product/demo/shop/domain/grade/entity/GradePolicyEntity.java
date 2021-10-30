package product.demo.shop.domain.grade.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import product.demo.shop.common.entity.AuditEntity;
import product.demo.shop.domain.grade.entity.enums.GradePolicyObject;
import product.demo.shop.domain.grade.entity.enums.GradePolicyType;
import product.demo.shop.domain.grade.entity.enums.UomType;

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
@NoArgsConstructor
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
    private UomType unitOfMeasure;

    @Column
    private BigDecimal appliedValue;

    @Column
    private String policyStatus;
}
