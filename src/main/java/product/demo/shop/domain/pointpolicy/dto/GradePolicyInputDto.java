package product.demo.shop.domain.pointpolicy.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class GradePolicyInputDto {

    @NotNull private GradeName gradeName;

    @NotNull private GradePolicyObject gradePolicyObject;

    @NotNull private BigDecimal originalPoint;

    private Long userInfoId;

    private Long orderId;

    public static GradePolicyInputDto of(
            GradeName gradeName, GradePolicyObject gradePolicyObject, BigDecimal originalPoint, Long userInfoId, Long orderId) {
        return new GradePolicyInputDto(gradeName, gradePolicyObject, originalPoint, userInfoId, orderId);
    }
}
