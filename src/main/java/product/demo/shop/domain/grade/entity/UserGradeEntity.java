package product.demo.shop.domain.grade.entity;

import lombok.*;
import product.demo.shop.common.entity.AuditEntity;
import product.demo.shop.domain.grade.entity.enums.GradeName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_USER_GRADE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class UserGradeEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGradeId;

    @Column(name="grade_name")
    @Enumerated(EnumType.STRING)
    @NonNull
    private GradeName gradeName;
}
