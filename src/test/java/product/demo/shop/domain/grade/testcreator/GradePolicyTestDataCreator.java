package product.demo.shop.domain.grade.testcreator;

import product.demo.shop.domain.grade.entity.UserGradeEntity;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.grade.repository.UserGradeRepository;
import product.demo.shop.domain.pointpolicy.entity.GradePolicyEntity;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyStatusType;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyType;
import product.demo.shop.domain.pointpolicy.entity.enums.MeasurementType;
import product.demo.shop.domain.pointpolicy.repository.GradePolicyRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/*
 * 샘플 데이터 생성을 위해
 * Repository 와 Service Test에서 공통적으로 사용되는 부분이라.
 * 외부로 따로 분리하였습니다.
 */
public class GradePolicyTestDataCreator {

    private final UserGradeRepository userGradeRepository;

    private final GradePolicyRepository gradePolicyRepository;

    public GradePolicyTestDataCreator(
            UserGradeRepository userGradeRepository, GradePolicyRepository gradePolicyRepository) {
        this.userGradeRepository = userGradeRepository;
        this.gradePolicyRepository = gradePolicyRepository;
    }

    public void setUp() {
        this.userGradeRepository.deleteAll();
        this.gradePolicyRepository.deleteAll();

        var gradeList = this.userGradeRepository.saveAll(testUserGradeList);
        this.gradePolicyRepository.saveAll(createTestGradePolicies(gradeList));
    }

    public static final List<UserGradeEntity> testUserGradeList =
            List.of(
                    UserGradeEntity.builder().gradeName(GradeName.BRONZE).build(),
                    UserGradeEntity.builder().gradeName(GradeName.SILVER).build(),
                    UserGradeEntity.builder().gradeName(GradeName.GOLD).build());

    private List<GradePolicyEntity> createTestGradePolicies(List<UserGradeEntity> gradeEntityList) {
        return List.of(
                //
                GradePolicyEntity.builder()
                        .policyName("POLICY" + UUID.randomUUID())
                        .userGradeId(gradeEntityList.get(0).getUserGradeId())
                        .policyObject(GradePolicyObject.POINT)
                        .policyType(GradePolicyType.INCREMENT)
                        .unitOfMeasure(MeasurementType.PERCENT)
                        .appliedValue(new BigDecimal(4.5))
                        .policyStatus(GradePolicyStatusType.ACTIVATE)
                        .build(),
                //
                GradePolicyEntity.builder()
                        .policyName("POLICY" + UUID.randomUUID())
                        .userGradeId(gradeEntityList.get(0).getUserGradeId())
                        .policyObject(GradePolicyObject.POINT)
                        .policyType(GradePolicyType.DECREMENT)
                        .unitOfMeasure(MeasurementType.PERCENT)
                        .appliedValue(new BigDecimal(23.55))
                        .policyStatus(GradePolicyStatusType.ACTIVATE)
                        .build(),
                //
                GradePolicyEntity.builder()
                        .policyName(UUID.randomUUID().toString())
                        .userGradeId(gradeEntityList.get(1).getUserGradeId())
                        .policyObject(GradePolicyObject.ORDER_PRICE)
                        .policyType(GradePolicyType.DECREMENT)
                        .unitOfMeasure(MeasurementType.POINT)
                        .appliedValue(new BigDecimal(5000))
                        .policyStatus(GradePolicyStatusType.ACTIVATE)
                        .build(),
                //
                GradePolicyEntity.builder()
                        .policyName(UUID.randomUUID().toString())
                        .userGradeId(gradeEntityList.get(1).getUserGradeId())
                        .policyObject(GradePolicyObject.ORDER_PRICE)
                        .policyType(GradePolicyType.DECREMENT)
                        .unitOfMeasure(MeasurementType.PERCENT)
                        .appliedValue(new BigDecimal(4.5))
                        .policyStatus(GradePolicyStatusType.ACTIVATE)
                        .build(),
                //
                GradePolicyEntity.builder()
                        .policyName(UUID.randomUUID().toString())
                        .userGradeId(gradeEntityList.get(2).getUserGradeId())
                        .policyObject(GradePolicyObject.POINT)
                        .policyType(GradePolicyType.INCREMENT)
                        .unitOfMeasure(MeasurementType.PERCENT)
                        .appliedValue(new BigDecimal(4.5))
                        .policyStatus(GradePolicyStatusType.HOLD)
                        .build(),
                //
                GradePolicyEntity.builder()
                        .policyName(UUID.randomUUID().toString())
                        .userGradeId(gradeEntityList.get(0).getUserGradeId())
                        .policyObject(GradePolicyObject.POINT)
                        .policyType(GradePolicyType.INCREMENT)
                        .unitOfMeasure(MeasurementType.PERCENT)
                        .appliedValue(new BigDecimal(4.5))
                        .policyStatus(GradePolicyStatusType.CLOSED)
                        .build(),
                //
                GradePolicyEntity.builder()
                        .policyName(UUID.randomUUID().toString())
                        .userGradeId(gradeEntityList.get(1).getUserGradeId())
                        .policyObject(GradePolicyObject.ORDER_PRICE)
                        .policyType(GradePolicyType.DECREMENT)
                        .unitOfMeasure(MeasurementType.PERCENT)
                        .appliedValue(new BigDecimal(40.5))
                        .policyStatus(GradePolicyStatusType.CLOSED)
                        .build(),
                //
                GradePolicyEntity.builder()
                        .policyName(UUID.randomUUID().toString())
                        .userGradeId(gradeEntityList.get(2).getUserGradeId())
                        .policyObject(GradePolicyObject.POINT)
                        .policyType(GradePolicyType.INCREMENT)
                        .unitOfMeasure(MeasurementType.POINT)
                        .appliedValue(new BigDecimal(5000))
                        .policyStatus(GradePolicyStatusType.ACTIVATE)
                        .build());
    }
}
