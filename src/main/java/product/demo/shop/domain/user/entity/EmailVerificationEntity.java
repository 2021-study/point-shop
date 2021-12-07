package product.demo.shop.domain.user.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import product.demo.shop.common.entity.AuditEntity;
import product.demo.shop.auth.dto.MailValidationDto;

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
@DynamicUpdate
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

    public static EmailVerificationEntity fromMailValidationDto(MailValidationDto mailValidationDto, long expirationSeconds) {
        return EmailVerificationEntity.builder()
                .userId(mailValidationDto.getUserInfoId())
                .verificationCode(mailValidationDto.getTokenString())
                .expiredDate(LocalDateTime.now().plusSeconds(expirationSeconds))
                .verificationCodeStatus("CREATED")
                .build();
    }

    public void changeVerifyCodeToConfirmed(String newVerificationCodeStatus) {
        // Enum으로 관리하면 편할듯.
        if(newVerificationCodeStatus.equals("CONFIRMED") && !this.verificationCodeStatus.equals("CREATED")){
            throw new RuntimeException("상태 변경을 위해 유효한 상태 값이 아닙니다.[현재 값 : " + this.verificationCodeStatus);
        }

        this.verificationCodeStatus = newVerificationCodeStatus;
    }

    public MailValidationDto toMailValidationDto(){
        return MailValidationDto.builder()
                .emailVerificationEntityId(this.emailVerificationCodeId)
                .tokenString(this.verificationCode)
                .build();
    }
}
