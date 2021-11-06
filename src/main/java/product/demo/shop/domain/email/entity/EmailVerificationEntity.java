package product.demo.shop.domain.email.entity;

import lombok.*;
import product.demo.shop.common.entity.AuditEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="TB_EMAIL_VERIFICATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class EmailVerificationEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailVerificationCodeId;

    @Column
    private long userId;

    @Column
    private String verificationCode;

    @Column
    private LocalDateTime expiredDate;

    @Column
    private String verificationCodeStatus;
}
