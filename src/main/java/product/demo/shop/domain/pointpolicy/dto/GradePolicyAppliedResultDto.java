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

    public void addResultPoint(BigDecimal operand){
        this.resultPoint = this.resultPoint.add(operand);
    }

    public void minusResultPoint(BigDecimal operand){
        this.resultPoint = this.resultPoint.subtract(operand);
    }
}
