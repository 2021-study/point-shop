package product.demo.shop.domain.user.grade.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.grade.repository.UserGradeRepository;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyDto;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyInputDto;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyType;
import product.demo.shop.domain.pointpolicy.entity.enums.MeasurementType;
import product.demo.shop.domain.pointpolicy.repository.GradePolicyRepository;
import product.demo.shop.domain.pointpolicy.service.GradePointPolicyProcessService;
import product.demo.shop.domain.user.grade.singleton.GradePolicySetupSingleton;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static product.demo.shop.domain.pointpolicy.service.GradePointPolicyProcessServiceImpl.PERCENTAGE_CONSTANT;

/* Mock 테스트가 아닌 실제 구현체를 테스트 합니다.*/
@SpringBootTest
@Slf4j
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GradePointPolicyProcessServiceTest {

    @Autowired UserGradeRepository userGradeRepository;

    @Autowired GradePolicyRepository gradePolicyRepository;

    @Autowired GradePointPolicyProcessService gradePointPolicyProcessService;

    @BeforeAll
    public void setUp() {
        GradePolicySetupSingleton.setUpSampleData(
                this.userGradeRepository, this.gradePolicyRepository);
    }

    static Stream<Arguments> generateGradePoliciesAppliedTestParams() throws Exception {
        return Stream.of(
                Arguments.of(
                        GradePolicyInputDto.of(
                                GradeName.BRONZE,
                                GradePolicyObject.POINT,
                                BigDecimal.valueOf(10_0000),
                                null,
                                null),
                        "등급 : BRONZE | 적용대상 : POINT | 적용 포인트 액 : 10,000"),
                Arguments.of(
                        GradePolicyInputDto.of(
                                GradeName.BRONZE,
                                GradePolicyObject.ORDER_PRICE,
                                BigDecimal.valueOf(10_0000),
                                null,
                                null),
                        "등급 : BRONZE | 적용대상 : ORDER_PRICE | 적용 포인트 액 : 10,000"),
                Arguments.of(
                        GradePolicyInputDto.of(
                                GradeName.SILVER,
                                GradePolicyObject.POINT,
                                BigDecimal.valueOf(10_0000),
                                null,
                                null),
                        "등급 : SILVER | 적용대상 : POINT | 적용 포인트 액 : 10,000"),
                Arguments.of(
                        GradePolicyInputDto.of(
                                GradeName.SILVER,
                                GradePolicyObject.ORDER_PRICE,
                                BigDecimal.valueOf(10_0000),
                                null,
                                null),
                        "등급 : SILVER | 적용대상 : ORDER_PRICE | 적용 포인트 액 : 10,000"),
                Arguments.of(
                        GradePolicyInputDto.of(
                                GradeName.GOLD,
                                GradePolicyObject.POINT,
                                BigDecimal.valueOf(10_0000),
                                null,
                                null),
                        "등급 : GOLD | 적용대상 : POINT | 적용 포인트 액 : 10,000"),
                Arguments.of(
                        GradePolicyInputDto.of(
                                GradeName.GOLD,
                                GradePolicyObject.ORDER_PRICE,
                                BigDecimal.valueOf(10_0000),
                                null,
                                null),
                        "등급 : GOLD | 적용대상 : ORDER_PRICE | 적용 포인트 액 : 10,000"),
                Arguments.of(
                        GradePolicyInputDto.of(
                                GradeName.BRONZE,
                                GradePolicyObject.POINT,
                                BigDecimal.valueOf(0),
                                null,
                                null),
                        "등급 : BRONZE | 적용대상 : POINT | 적용 포인트 액 : 0"));
    }

    @ParameterizedTest(name = "{index}: {1}")
    @MethodSource("generateGradePoliciesAppliedTestParams")
    @DisplayName("등급에 따른 최종 적용 포인트 계산 테스트 케이스들이 모두 성공하여야 한다.")
    public void calculateGradePolicyAppliedPoints_success(
            GradePolicyInputDto testDto, String message) {
        var result = this.gradePointPolicyProcessService.appliedGradePolicy(testDto);
        log.info(">>> Result : " + result.getResultPoint());

        var expectationPoint =
                generateExpectationValue(
                        testDto, findGradePolicyListForCalculateExpectationValue(testDto));
        assertEquals(expectationPoint.longValue(), result.getResultPoint().longValue());
    }

    private List<GradePolicyDto> findGradePolicyListForCalculateExpectationValue(
            GradePolicyInputDto dto) {
        return this.gradePolicyRepository.findGradePoliciesByGradeName(
                dto.getGradeName(), dto.getGradePolicyObject());
    }

    /* TODO : (고민거리....) 테스트에 필요한 Expectation을 테스트 케이스별로 동적으로 Generation 해주기 위해 만든 이 함수는 올바른 비즈니스 결과를 리턴한다고 보장할 수 있을까?
      얘도 똑같이 신뢰성이 보장된 기능이 아닌데 다른 로직 테스트에 사용할만 한건가??
      테스트 품질과 신뢰성을 위해 바람직한 로직 테스트 방법은 어떤 것일까?
      이런 것을 TDD로 접근했으면 조금 더 깔끔하지 않앗을까??
    */
    private BigDecimal generateExpectationValue(
            GradePolicyInputDto testDto, List<GradePolicyDto> expectedGradePolicyList) {
        var testModelList =
                expectedGradePolicyList.stream()
                        .map(
                                (it) -> {
                                    if (it.getUnitOfMeasure().equals(MeasurementType.PERCENT)) {
                                        return GradePolicyAppliedPointTestModel.of(
                                                it.getPolicyType(),
                                                it.getUnitOfMeasure(),
                                                it.getAppliedValue()
                                                        .multiply(
                                                                BigDecimal.valueOf(
                                                                        PERCENTAGE_CONSTANT)));
                                    } else {
                                        return GradePolicyAppliedPointTestModel.of(
                                                it.getPolicyType(),
                                                it.getUnitOfMeasure(),
                                                it.getAppliedValue());
                                    }
                                })
                        .collect(Collectors.toList());

        var expectationPoint = testDto.getOriginalPoint();

        for (GradePolicyAppliedPointTestModel model : testModelList) {
            if (model.getGradePolicyType().equals(GradePolicyType.INCREMENT)) {
                if (model.getMeasurementType().equals(MeasurementType.PERCENT)) {
                    expectationPoint =
                            expectationPoint.add(
                                    testDto.getOriginalPoint()
                                            .multiply(model.getMidAppliedValue()));
                } else {
                    expectationPoint = expectationPoint.add(model.getMidAppliedValue());
                }
            } else if (model.getGradePolicyType().equals(GradePolicyType.DECREMENT)) {
                if (model.getMeasurementType().equals(MeasurementType.PERCENT)) {
                    expectationPoint =
                            expectationPoint.subtract(
                                    testDto.getOriginalPoint()
                                            .multiply(model.getMidAppliedValue()));
                } else {
                    expectationPoint = expectationPoint.subtract(model.getMidAppliedValue());
                }
            } else {
                // empty
            }
            log.info(">> 중간 점검 : " + expectationPoint);
        }
        log.info(">> expecation Point : " + expectationPoint);

        return expectationPoint;
    }
}
