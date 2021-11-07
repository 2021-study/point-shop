package product.demo.shop.domain.verification.entity;

import lombok.*;
import product.demo.shop.common.entity.AuditEntity;
import product.demo.shop.domain.verification.enums.VerificationCodeStatus;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private VerificationCodeStatus verificationCodeStatus;

    public void useCode(){
        this.verificationCodeStatus = VerificationCodeStatus.CONFIRMED;
    }
}
