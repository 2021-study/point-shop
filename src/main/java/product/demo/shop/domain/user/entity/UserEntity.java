package product.demo.shop.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import product.demo.shop.common.converter.CryptoConverter;
import product.demo.shop.common.entity.AuditEntity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "TB_USER_INFO")
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class UserEntity extends AuditEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userInfoId;

    @Column
    private long userGradeId;

    @Column
    private String userAccountId;

    @Column
    private String snsProviderType;

    @Column
    @Convert(converter = CryptoConverter.class)
    private String name;

    @Column
    @Convert(converter = CryptoConverter.class)
    private String email;

    @Column
    private String phone;

    @Column
    @Convert(converter = CryptoConverter.class)
    private String address;

    @Column
    private String password; // TODO : Password는 단방향 해싱이 되어야 한다.

    @Column
    private String emailVerificationStatus;

    @Column
    private String userStatusCode;

    public static UserEntity sampleUser(){

        return new UserEntity(
                null,
                1L,
                "sampleUser",
                "sns_provider",
                "sample",
                "sample@email.com",
                "password",
                "010-1111-1111",
                "address",
                "READY",
                "0001"
        );
    }
}
