package product.demo.shop.domain.user.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import product.demo.shop.common.converter.CryptoConverter;
import product.demo.shop.common.entity.AuditEntity;
import product.demo.shop.auth.dto.SignupDto;
import product.demo.shop.domain.grade.entity.UserGradeEntity;
import product.demo.shop.domain.user.entity.enums.UserStatusType;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "TB_USER_INFO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userInfoId;

    @Column private long userGradeId;

    @Column private String userAccountId;

    @Column private String snsProviderType;

    @Column
    @Convert(converter = CryptoConverter.class)
    private String name;

    @Column
    @Convert(converter = CryptoConverter.class)
    private String email;

    @Column private String phone;

    @Column
    @Convert(converter = CryptoConverter.class)
    private String address;

    @Column private String password; // TODO : Password는 단방향 해싱이 되어야 한다.

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatusType userStatus;

    public static UserEntity fromUserDtoWithStandAlone(
            SignupDto signupDto, UserGradeEntity userGradeEntity, PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .email(signupDto.getEmail())
                .userAccountId(signupDto.getUserAccountId())
                .address(signupDto.getAddress())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .snsProviderType("NONE")
                .userGradeId(userGradeEntity.getUserGradeId())
                .userStatus(UserStatusType.REGISTERED_NOT_CONFIRMED) // 메일 미인증 상태
                .phone(signupDto.getPhoneNumber())
                .name(signupDto.getName())
                .build();
    }

    public void changeUserStatusConfirmed() {
        if (this.userStatus.equals(UserStatusType.REGISTERED_NOT_CONFIRMED)) {
            this.userStatus = UserStatusType.VERIFIED;
        } else {
            throw new RuntimeException(
                    "변경 가능한 상태가 아닙니다. 현재상태 : ["
                            + this.userStatus.name()
                            + " / "
                            + this.userStatus.getValue()
                            + " ]");
        }
    }
}
