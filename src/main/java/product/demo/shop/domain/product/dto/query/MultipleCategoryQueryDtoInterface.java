package product.demo.shop.domain.product.dto.query;

public interface MultipleCategoryQueryDtoInterface {
    Long getBigProductCategoryId();

    Long getBigParent();

    String getBigCategoryName();

    Long getMiddleProductCategoryId();

    Long getMiddleParent();

    String getMiddleProductCategoryName();

    Long getSmallProductCategoryId();

    Long getSmallParent();

    String getSmallProductCategoryName();
}
