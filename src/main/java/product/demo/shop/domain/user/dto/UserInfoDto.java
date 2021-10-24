package product.demo.shop.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import product.demo.shop.domain.grade.entity.enums.GradeName;

@Getter
@NoArgsConstructor
public class UserInfoDto {
    private long userId;
    private long userGradeId;
    private GradeName userGradeName;
    private String email;

    @QueryProjection
    public UserInfoDto(
            long userId,
            long userGradeId,
            GradeName userGradeName,
            String email
    ){
        this.userId = userId;
        this.userGradeId = userGradeId;
        this.userGradeName = userGradeName;
        this.email = email;
    }
}
