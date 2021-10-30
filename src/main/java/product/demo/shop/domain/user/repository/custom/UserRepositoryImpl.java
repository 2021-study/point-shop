package product.demo.shop.domain.user.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import product.demo.shop.domain.grade.entity.QUserGradeEntity;
import product.demo.shop.domain.user.dto.QUserInfoDto;
import product.demo.shop.domain.user.dto.UserInfoDto;
import product.demo.shop.domain.user.entity.QUserEntity;

@Repository
@Slf4j
public class UserRepositoryImpl implements CustomUserRepository{

    private JPAQueryFactory jpaQueryFactory;
    private QUserEntity qUserEntity;
    private QUserGradeEntity qUserGradeEntity;

    public UserRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
        this.qUserEntity = QUserEntity.userEntity;
        this.qUserGradeEntity = QUserGradeEntity.userGradeEntity;
    }

    @Override
    public UserInfoDto findUserWithUserGradeInfo(Long userId) {
        UserInfoDto queryResult = jpaQueryFactory
                .select(new QUserInfoDto(
                        qUserEntity.userInfoId,
                        qUserEntity.userGradeId,
                        qUserGradeEntity.gradeName,
                        qUserEntity.email
                ))
                .from(qUserEntity)
                .leftJoin(qUserGradeEntity)
                .on(qUserEntity.userGradeId.eq(qUserGradeEntity.userGradeId))
                .fetchOne();

        return queryResult;
    }
}
