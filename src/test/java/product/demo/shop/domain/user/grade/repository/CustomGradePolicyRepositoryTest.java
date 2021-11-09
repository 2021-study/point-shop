package product.demo.shop.domain.user.grade.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import product.demo.shop.common.entity.AccountAuditAware;
import product.demo.shop.configuration.DataBaseConfiguration;
import product.demo.shop.configuration.QuerydslConfig;
import product.demo.shop.domain.grade.entity.UserGradeEntity;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.grade.repository.UserGradeRepository;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementDto;
import product.demo.shop.domain.pointpolicy.entity.GradePolicyEntity;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyStatusType;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyType;
import product.demo.shop.domain.pointpolicy.entity.enums.MeasurementType;
import product.demo.shop.domain.pointpolicy.repository.CustomGradePolicyRepositoryImpl;
import product.demo.shop.domain.pointpolicy.repository.GradePolicyRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@ExtendWith(SpringExtension.class)
@DataJpaTest(
        includeFilters =
                @ComponentScan.Filter(
                        type = ASSIGNABLE_TYPE,
                        classes = {
                            AccountAuditAware.class,
                            DataBaseConfiguration.class,
                            QuerydslConfig.class
                        }))
@ActiveProfiles("test")
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomGradePolicyRepositoryTest {

    @Autowired CustomGradePolicyRepositoryImpl customGradePolicyRepositoryImpl;

    @Autowired UserGradeRepository userGradeRepository;

    @Autowired GradePolicyRepository gradePolicyRepository;

    static final List<UserGradeEntity> testUserGradeList =
            List.of(
                    UserGradeEntity.builder().gradeName(GradeName.BRONZE).build(),
                    UserGradeEntity.builder().gradeName(GradeName.SILVER).build(),
                    UserGradeEntity.builder().gradeName(GradeName.GOLD).build());

    static final List<GradePolicyEntity> testGradePolicies =
            List.of(
                    //
                    GradePolicyEntity.builder()
                            .policyName("POLICY" + UUID.randomUUID())
                            .userGradeId(1L)
                            .policyObject(GradePolicyObject.POINT)
                            .policyType(GradePolicyType.INCREMENT)
                            .unitOfMeasure(MeasurementType.PERCENT)
                            .appliedValue(new BigDecimal(4.5))
                            .policyStatus(GradePolicyStatusType.ACTIVATE)
                            .build(),
                    //
                    GradePolicyEntity.builder()
                            .policyName("POLICY" + UUID.randomUUID())
                            .userGradeId(1L)
                            .policyObject(GradePolicyObject.POINT)
                            .policyType(GradePolicyType.DECREMENT)
                            .unitOfMeasure(MeasurementType.PERCENT)
                            .appliedValue(new BigDecimal(23.55))
                            .policyStatus(GradePolicyStatusType.ACTIVATE)
                            .build(),
                    //
                    GradePolicyEntity.builder()
                            .policyName(UUID.randomUUID().toString())
                            .userGradeId(2L)
                            .policyObject(GradePolicyObject.ORDER_PRICE)
                            .policyType(GradePolicyType.DECREMENT)
                            .unitOfMeasure(MeasurementType.POINT)
                            .appliedValue(new BigDecimal(5000))
                            .policyStatus(GradePolicyStatusType.ACTIVATE)
                            .build(),
                    //
                    GradePolicyEntity.builder()
                            .policyName(UUID.randomUUID().toString())
                            .userGradeId(2L)
                            .policyObject(GradePolicyObject.ORDER_PRICE)
                            .policyType(GradePolicyType.DECREMENT)
                            .unitOfMeasure(MeasurementType.PERCENT)
                            .appliedValue(new BigDecimal(4.5))
                            .policyStatus(GradePolicyStatusType.ACTIVATE)
                            .build(),
                    //
                    GradePolicyEntity.builder()
                            .policyName(UUID.randomUUID().toString())
                            .userGradeId(3L)
                            .policyObject(GradePolicyObject.POINT)
                            .policyType(GradePolicyType.INCREMENT)
                            .unitOfMeasure(MeasurementType.PERCENT)
                            .appliedValue(new BigDecimal(4.5))
                            .policyStatus(GradePolicyStatusType.HOLD)
                            .build(),
                    //
                    GradePolicyEntity.builder()
                            .policyName(UUID.randomUUID().toString())
                            .userGradeId(1L)
                            .policyObject(GradePolicyObject.POINT)
                            .policyType(GradePolicyType.INCREMENT)
                            .unitOfMeasure(MeasurementType.PERCENT)
                            .appliedValue(new BigDecimal(4.5))
                            .policyStatus(GradePolicyStatusType.CLOSED)
                            .build(),
                    //
                    GradePolicyEntity.builder()
                            .policyName(UUID.randomUUID().toString())
                            .userGradeId(2L)
                            .policyObject(GradePolicyObject.ORDER_PRICE)
                            .policyType(GradePolicyType.DECREMENT)
                            .unitOfMeasure(MeasurementType.PERCENT)
                            .appliedValue(new BigDecimal(40.5))
                            .policyStatus(GradePolicyStatusType.CLOSED)
                            .build(),
                    //
                    GradePolicyEntity.builder()
                            .policyName(UUID.randomUUID().toString())
                            .userGradeId(3L)
                            .policyObject(GradePolicyObject.POINT)
                            .policyType(GradePolicyType.INCREMENT)
                            .unitOfMeasure(MeasurementType.POINT)
                            .appliedValue(new BigDecimal(5000))
                            .policyStatus(GradePolicyStatusType.ACTIVATE)
                            .build());

    static Stream<Arguments> generateGradePoliciesTestParams() throws Exception {
        return Stream.of(
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                GradeName.BRONZE, null, GradePolicyStatusType.ACTIVATE),
                        2,
                        "[WHERE 조건] : GRADE_NAME = 'BRONZE' AND POLICY_STATUS = 'ACTIVATE'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                GradeName.SILVER, null, GradePolicyStatusType.ACTIVATE),
                        2,
                        "[WHERE 조건] : GRADE_NAME = 'SILVER' AND POLICY_STATUS = 'ACTIVATE'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                GradeName.GOLD, null, GradePolicyStatusType.ACTIVATE),
                        1,
                        "[WHERE 조건] : GRADE_NAME = 'GOLD' AND POLICY_STATUS = 'ACTIVATE'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(GradeName.BRONZE, null, null),
                        3,
                        "[WHERE 조건] : GRADE_NAME = 'BRONZE'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(null, "POLICY", null),
                        2,
                        "[WHERE 조건] : POLICY_NAME like '%POLICY%'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                null, null, GradePolicyStatusType.ACTIVATE),
                        5,
                        "[WHERE 조건] : POLICY_STATUS ='ACTIVATE'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                GradeName.SILVER, null, GradePolicyStatusType.CLOSED),
                        1,
                        "[WHERE 조건] : POLICY_STATUS ='CLOSED'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                GradeName.GOLD, null, GradePolicyStatusType.HOLD),
                        1,
                        "[WHERE 조건] : GRADE_NAME='GOLD' AND POLICY_STATUS ='HOLD'"));
    }

    static Stream<Arguments> generatePageableTestParams() throws Exception {
        return Stream.of(
                Arguments.of(PageRequest.of(0, Integer.MAX_VALUE), 8, "페이징 없음. 한 페이지에 모든 정보들을 쿼리"),
                Arguments.of(PageRequest.of(0, 2), 2, "한 페이지에 보여주는 데이터는 2개 / 첫번 째 페이지"),
                Arguments.of(PageRequest.of(1, 2), 2, "한 페이지에 보여주는 데이터는 2개 / 두번째 페이지"),
                Arguments.of(PageRequest.of(0, 5), 5, "한 페이지에 보여주는 데이터는 5개 / 첫 번째 페이지 "),
                Arguments.of(PageRequest.of(1, 5), 3, "한 페이지에 보여주는 데이터는 5개 / 두 번째 페이지 "),
                Arguments.of(
                        PageRequest.of(2, 5), 0, "한 페이지에 보여주는 데이터는 5개 / 세 번째 페이지 -> 결과는 0이여야 한다."));
    }

    @BeforeAll
    public void setUp() {
        userGradeRepository.saveAllAndFlush(testUserGradeList);
        gradePolicyRepository.saveAllAndFlush(testGradePolicies);
    }

    @ParameterizedTest(name = "{index}: {2}")
    @MethodSource("generateGradePoliciesTestParams")
    @DisplayName("등급별 정책 조회 테스트(Where 조건 적용)")
    public void findGradePoliciesQueryTest(
            GradePointPolicyManagementDto conditions, int expect, String message) {
        var testPage = PageRequest.of(0, Integer.MAX_VALUE);
        var findResults = customGradePolicyRepositoryImpl.findGradePolicies(conditions, testPage);

        System.out.println("TOTAL : ::: " + findResults.getNumberOfElements());
        assertNotNull(findResults);
        assertTrue(findResults.getNumberOfElements() > 0);
        assertEquals(expect, findResults.getNumberOfElements());
    }

    @ParameterizedTest(name = "{index}: {2}")
    @MethodSource("generatePageableTestParams")
    @DisplayName("등급별 정책 조회 테스트(페이징 처리 확인)")
    public void findGradePoliciesPagingTest(Pageable pageable, int expectValue, String message) {
        var findResults =
                customGradePolicyRepositoryImpl.findGradePolicies(
                        GradePointPolicyManagementDto.of(null, null, null), pageable);

        System.out.println("TOTAL : ::: " + findResults.getNumberOfElements());
        assertTrue(findResults.getNumberOfElements() >= 0);
        assertEquals(expectValue, findResults.getNumberOfElements());
    }
}
