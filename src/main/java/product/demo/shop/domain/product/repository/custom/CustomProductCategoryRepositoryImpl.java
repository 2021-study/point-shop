package product.demo.shop.domain.product.repository.custom;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import product.demo.shop.domain.product.dto.query.QSingleProductCategoryQueryDto;
import product.demo.shop.domain.product.dto.query.SingleProductCategoryQueryDto;
import product.demo.shop.domain.product.entity.QProductCategoryEntity;

@Repository
public class CustomProductCategoryRepositoryImpl implements CustomProductCategoryRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final QProductCategoryEntity qProductCategoryEntity;

    public CustomProductCategoryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.qProductCategoryEntity = QProductCategoryEntity.productCategoryEntity;
    }

    @Override
    public SingleProductCategoryQueryDto findProductCategory(Long categoryId, String categoryName) {

        return jpaQueryFactory
                .select(
                        new QSingleProductCategoryQueryDto(
                                qProductCategoryEntity.productCategoryId,
                                qProductCategoryEntity.parent,
                                qProductCategoryEntity.productCategoryName))
                .from(qProductCategoryEntity)
                .where(
                        eqProductCategoryId(categoryId),
                        eqProductionCategoryName(categoryName))
                .fetchOne();
    }

    private BooleanExpression eqProductCategoryId(Long categoryId) {
        if(categoryId==null) {
            return null;
        }

        return this.qProductCategoryEntity.productCategoryId.eq(categoryId);
    }

    private BooleanExpression eqProductionCategoryName(String categoryName) {
        if(categoryName==null) {
            return null;
        }
        return this.qProductCategoryEntity.productCategoryName.eq(categoryName);
    }
}