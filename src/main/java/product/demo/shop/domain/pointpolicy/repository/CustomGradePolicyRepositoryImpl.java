package product.demo.shop.domain.pointpolicy.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import product.demo.shop.domain.grade.entity.QUserGradeEntity;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementDto;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyDto;
import product.demo.shop.domain.pointpolicy.dto.QGradePolicyDto;
import product.demo.shop.domain.pointpolicy.entity.QGradePolicyEntity;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyStatusType;

import java.util.List;

@Repository
public class CustomGradePolicyRepositoryImpl implements CustomGradePolicyRepository {

    private JPAQueryFactory jpaQueryFactory;
    private QGradePolicyEntity qGradePolicyEntity;
    private QUserGradeEntity qUserGradeEntity;

    public CustomGradePolicyRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.qGradePolicyEntity = QGradePolicyEntity.gradePolicyEntity;
        this.qUserGradeEntity = QUserGradeEntity.userGradeEntity;
    }

    @Override
    public PageImpl<GradePolicyDto> findGradePolicies(
            GradePointPolicyManagementDto dto, Pageable pageable) {
        QueryResults<GradePolicyDto> queryResult =
                jpaQueryFactory
                        .select(
                                new QGradePolicyDto(
                                        qGradePolicyEntity.gradePolicyId,
                                        qGradePolicyEntity.userGradeId,
                                        qUserGradeEntity.gradeName,
                                        qGradePolicyEntity.policyObject,
                                        qGradePolicyEntity.policyType,
                                        qGradePolicyEntity.unitOfMeasure,
                                        qGradePolicyEntity.policyName,
                                        qGradePolicyEntity.appliedValue,
                                        qGradePolicyEntity.policyStatus))
                        .from(qGradePolicyEntity)
                        .innerJoin(qUserGradeEntity)
                        .on(qGradePolicyEntity.userGradeId.eq(qUserGradeEntity.userGradeId))
                        .where(
                                containGradePolicyName(dto.getPolicyName())
                                        ,(eqGradeName(dto.getGradeName()))
                                        ,(eqPolicyStatus(dto.getPolicyStatus())))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetchResults();

        return new PageImpl<>(queryResult.getResults(), pageable, queryResult.getTotal());
    }

    @Override
    public List<GradePolicyDto> findGradePoliciesByGradeName(GradeName gradeName, GradePolicyObject objectType) {
        return jpaQueryFactory
                .select(
                        new QGradePolicyDto(
                                qGradePolicyEntity.gradePolicyId,
                                qGradePolicyEntity.userGradeId,
                                qUserGradeEntity.gradeName,
                                qGradePolicyEntity.policyObject,
                                qGradePolicyEntity.policyType,
                                qGradePolicyEntity.unitOfMeasure,
                                qGradePolicyEntity.policyName,
                                qGradePolicyEntity.appliedValue,
                                qGradePolicyEntity.policyStatus))
                .from(qGradePolicyEntity)
                .innerJoin(qUserGradeEntity)
                .on(qGradePolicyEntity.userGradeId.eq(qUserGradeEntity.userGradeId))
                .where(
                        eqGradeName(gradeName)
                                .and(qGradePolicyEntity.policyObject.eq(objectType))
                                .and(
                                        qGradePolicyEntity.policyStatus.eq(
                                                GradePolicyStatusType.ACTIVATE))) // 활성화된 정책만 쿼리해온다.
                .fetch();
    }

    private BooleanExpression containGradePolicyName(String gradePolicyName) {
        if (!StringUtils.hasText(gradePolicyName)) {
            return null;
        }
        return this.qGradePolicyEntity.policyName.containsIgnoreCase(gradePolicyName);
    }

    private BooleanExpression eqGradeName(GradeName gradeName) {
        if (gradeName == null) {
            return null;
        }
        return this.qUserGradeEntity.gradeName.eq(gradeName);
    }

    private BooleanExpression eqPolicyStatus(GradePolicyStatusType statusType) {
        if (statusType == null) {
            return null;
        }

        return this.qGradePolicyEntity.policyStatus.eq(statusType);
    }
}
