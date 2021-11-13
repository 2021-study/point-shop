package product.demo.shop.domain.pointpolicy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyAppliedResultDto;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyDto;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyInputDto;
import product.demo.shop.domain.pointpolicy.entity.enums.MeasurementType;
import product.demo.shop.domain.pointpolicy.exception.GradePointPolicyErrorCode;
import product.demo.shop.domain.pointpolicy.exception.GradePointPolicyException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class GradePointPolicyProcessServiceImpl implements GradePointPolicyProcessService {

    private final CacheableGradePolicyServiceImpl cacheableGradePolicyService;
    public static final double PERCENTAGE_CONSTANT = 0.01d;

    @Override
    public GradePolicyAppliedResultDto appliedGradePolicy(@Valid GradePolicyInputDto inputDto) {
        var policyList =
                cacheableGradePolicyService.findByGradePolicyIdFromCache(
                        inputDto.getGradeName(), inputDto.getGradePolicyObject());

        var appliedResult = new GradePolicyAppliedResultDto(inputDto.getOriginalPoint(), inputDto);
        for (GradePolicyDto dto : policyList) {
            log.info("===>" + dto);
            var operand =
                    applyMeasureTypeCalculate(
                            inputDto.getOriginalPoint(),
                            dto.getUnitOfMeasure(),
                            dto.getAppliedValue());
            switch (dto.getPolicyType()) {
                case INCREMENT:
                    appliedResult.addResultPoint(operand);
                    break;
                case DECREMENT:
                    appliedResult.minusResultPoint(operand);
                    break;
                default:
                    // default가 수행 될 가능성은 없으나, 코드 안정성을 위해 정의함.
                    throw new GradePointPolicyException(
                            GradePointPolicyErrorCode.INVALID_POLICY_TYPE);
            }
        }

        log.info("===>" + appliedResult);
        return appliedResult;
    }

    private BigDecimal applyMeasureTypeCalculate(
            BigDecimal beforeValue, MeasurementType type, BigDecimal appliedValue) {
        switch (type) {
            case POINT: // 정액
                // 계산 결과를 1의 자리 절삭
                return appliedValue.setScale(-1, RoundingMode.DOWN);
            case PERCENT: // 정률
                // 계산 결과를 1의 자리 절삭
                // 백분위 이므로 DB에 등록된 값의 1/100 으로 연산을 한다.
                return beforeValue
                        .multiply(appliedValue.multiply(BigDecimal.valueOf(PERCENTAGE_CONSTANT)))
                        .setScale(-1, RoundingMode.DOWN);
            default:
                // default가 수행 될 가능성은 없으나, 코드 안정성을 위해 정의함.
                throw new GradePointPolicyException(
                        GradePointPolicyErrorCode.INVALID_MEASUREMENT_TYPE);
        }
    }
}
