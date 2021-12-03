package product.demo.shop.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import product.demo.shop.domain.grade.entity.enums.GradeName;

// User 사용자에 대한 개괄적인 정보 제공 DTO
@Getter
@NoArgsConstructor
@ToString
public class UserInfoDto {
    private long userId;
    private String userAccountId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private long userGradeId;

    // 등급 관련
    private GradeName userGradeName;

    // 포인트 관련
    private int totalUsablePoint; // 전체 사용가능 포인트

    // 주문 관련 (추가 예정)

    @QueryProjection
    public UserInfoDto(
            long userId,
            String userAccountId,
            String name,
            String email,
            String phone,
            String address,
            long userGradeId,
            GradeName userGradeName,
            int totalUsablePoint) {

        this.userId = userId;
        this.userAccountId = userAccountId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.userGradeId = userGradeId;
        this.userGradeName = userGradeName;
        this.totalUsablePoint = totalUsablePoint;
    }
}
