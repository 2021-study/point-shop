package product.demo.shop.domain.grade.repository;

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
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementDto;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyStatusType;
import product.demo.shop.domain.pointpolicy.repository.CustomGradePolicyRepositoryImpl;
import product.demo.shop.domain.pointpolicy.repository.GradePolicyRepository;
import product.demo.shop.domain.grade.testcreator.GradePolicyTestDataCreator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    static Stream<Arguments> generateGradePoliciesTestParams() throws Exception {
        return Stream.of(
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                GradeName.BRONZE, null, GradePolicyStatusType.ACTIVATE),
                        2,
                        "[WHERE ??????] : GRADE_NAME = 'BRONZE' AND POLICY_STATUS = 'ACTIVATE'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                GradeName.SILVER, null, GradePolicyStatusType.ACTIVATE),
                        2,
                        "[WHERE ??????] : GRADE_NAME = 'SILVER' AND POLICY_STATUS = 'ACTIVATE'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                GradeName.GOLD, null, GradePolicyStatusType.ACTIVATE),
                        1,
                        "[WHERE ??????] : GRADE_NAME = 'GOLD' AND POLICY_STATUS = 'ACTIVATE'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(GradeName.BRONZE, null, null),
                        3,
                        "[WHERE ??????] : GRADE_NAME = 'BRONZE'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(null, "POLICY", null),
                        2,
                        "[WHERE ??????] : POLICY_NAME like '%POLICY%'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                null, null, GradePolicyStatusType.ACTIVATE),
                        5,
                        "[WHERE ??????] : POLICY_STATUS ='ACTIVATE'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                GradeName.SILVER, null, GradePolicyStatusType.CLOSED),
                        1,
                        "[WHERE ??????] : POLICY_STATUS ='CLOSED'"),
                Arguments.of(
                        GradePointPolicyManagementDto.of(
                                GradeName.GOLD, null, GradePolicyStatusType.HOLD),
                        1,
                        "[WHERE ??????] : GRADE_NAME='GOLD' AND POLICY_STATUS ='HOLD'"));
    }

    static Stream<Arguments> generatePageableTestParams() throws Exception {
        return Stream.of(
                Arguments.of(PageRequest.of(0, Integer.MAX_VALUE), 8, "????????? ??????. ??? ???????????? ?????? ???????????? ??????"),
                Arguments.of(PageRequest.of(0, 2), 2, "??? ???????????? ???????????? ???????????? 2??? / ?????? ??? ?????????"),
                Arguments.of(PageRequest.of(1, 2), 2, "??? ???????????? ???????????? ???????????? 2??? / ????????? ?????????"),
                Arguments.of(PageRequest.of(0, 5), 5, "??? ???????????? ???????????? ???????????? 5??? / ??? ?????? ????????? "),
                Arguments.of(PageRequest.of(1, 5), 3, "??? ???????????? ???????????? ???????????? 5??? / ??? ?????? ????????? "),
                Arguments.of(
                        PageRequest.of(2, 5), 0, "??? ???????????? ???????????? ???????????? 5??? / ??? ?????? ????????? -> ????????? 0????????? ??????."));
    }

    static Stream<Arguments> generateGradePolicyNameQueryTestPrams() throws Exception {
        return Stream.of(
                Arguments.of(
                        GradeName.BRONZE, GradePolicyObject.POINT, 2, "[WHERE ??????] : BRONZE, POINT"),
                Arguments.of(GradeName.BRONZE, null, 2, "[WHERE ??????] : BRONZE, POINT"),
                Arguments.of(null, GradePolicyObject.POINT, 3, "[WHERE ??????] : BRONZE, POINT"),
                Arguments.of(null, null, 5, "[WHERE ??????] : ??????"));
    }

    @BeforeAll
    public void setUp() {
        var creator =
                new GradePolicyTestDataCreator(
                        this.userGradeRepository, this.gradePolicyRepository);
        creator.setUp();
    }

    @ParameterizedTest(name = "{index}: {2}")
    @MethodSource("generateGradePoliciesTestParams")
    @DisplayName("????????? ?????? ?????? ?????????(Where ?????? ??????)")
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
    @DisplayName("????????? ?????? ?????? ?????????(????????? ?????? ??????)")
    public void findGradePoliciesPagingTest(Pageable pageable, int expectValue, String message) {
        var findResults =
                customGradePolicyRepositoryImpl.findGradePolicies(
                        GradePointPolicyManagementDto.of(null, null, null), pageable);

        System.out.println("TOTAL : ::: " + findResults.getNumberOfElements());
        assertTrue(findResults.getNumberOfElements() >= 0);
        assertEquals(expectValue, findResults.getNumberOfElements());
    }

    @ParameterizedTest(name = "{index}: {3}")
    @MethodSource("generateGradePolicyNameQueryTestPrams")
    @DisplayName("?????? ????????? ????????? API(findGradePoliciesByGradeName) ?????????")
    public void findGradePoliciesByGradeNameTest(
            GradeName gradeName, GradePolicyObject policyObject, int expect, String message) {
        var findResult =
                customGradePolicyRepositoryImpl.findGradePoliciesByGradeName(
                        gradeName, policyObject);

        assertFalse(findResult.isEmpty());
        System.out.println("TOTAL : ::: " + findResult.size());
        assertEquals(expect, findResult.size());
    }
}
