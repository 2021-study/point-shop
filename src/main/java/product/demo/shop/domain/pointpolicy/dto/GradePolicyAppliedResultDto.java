package product.demo.shop.domain.pointpolicy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GradePolicyAppliedResultDto {

    private BigDecimal resultPoint;

    private GradePolicyInputDto inputDto;

    public void addResultPoint(BigDecimal operand) {
        /* thread Safe 하지 않은 메서드 입니다.*/
        this.resultPoint = this.resultPoint.add(operand);
    }

    public void minusResultPoint(BigDecimal operand) {
        /* thread Safe 하지 않은 메서드 입니다.*/
        this.resultPoint = this.resultPoint.subtract(operand);
    }
}
