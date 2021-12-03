package product.demo.shop.domain.user.repository.custom;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import product.demo.shop.domain.grade.entity.QUserGradeEntity;
import product.demo.shop.domain.point.entity.QPointEventEntity;
import product.demo.shop.domain.user.dto.QUserInfoDto;
import product.demo.shop.domain.user.dto.UserInfoDto;
import product.demo.shop.domain.user.entity.QUserEntity;

@Repository
@Slf4j
public class UserRepositoryImpl implements CustomUserRepository {

    private JPAQueryFactory jpaQueryFactory;
    private QUserEntity qUserEntity;
    private QUserGradeEntity qUserGradeEntity;
    private QPointEventEntity qPointEventEntity;

    public UserRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.qUserEntity = QUserEntity.userEntity;
        this.qUserGradeEntity = QUserGradeEntity.userGradeEntity;
        this.qPointEventEntity = QPointEventEntity.pointEventEntity;
    }

    @Override
    public UserInfoDto findMyUserComprehensiveInfo(Long userId) {
        UserInfoDto queryResult =
                jpaQueryFactory
                        .select(
                                new QUserInfoDto(
                                        qUserEntity.userInfoId,
                                        qUserEntity.userAccountId,
                                        qUserEntity.name,
                                        qUserEntity.email,
                                        qUserEntity.phone,
                                        qUserEntity.address,
                                        qUserEntity.userGradeId,
                                        qUserGradeEntity.gradeName,
                                        ExpressionUtils.as(
                                                JPAExpressions.select(qPointEventEntity.point.sum())
                                                        .from(qPointEventEntity)
                                                        .groupBy(qPointEventEntity.userInfoId)
                                                        .having(
                                                                qPointEventEntity.userInfoId.eq(
                                                                        userId)),
                                                "totalUsablePoint"))) // 유저별 총 가용 포인트 조회 서브 쿼리
                        .from(qUserEntity)
                        .innerJoin(qUserGradeEntity)
                        .on(qUserEntity.userGradeId.eq(qUserGradeEntity.userGradeId))
                        .where(qUserEntity.userInfoId.eq(userId))
                        .fetchOne();

        return queryResult;
    }
}
