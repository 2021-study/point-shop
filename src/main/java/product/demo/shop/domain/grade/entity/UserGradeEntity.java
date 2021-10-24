package product.demo.shop.domain.grade.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
@NoArgsConstructor
@RequiredArgsConstructor
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
