package product.demo.shop.domain.product.dto.query;

public interface MultipleCategoryQueryDtoInterface {
    Long getBigProductCategoryId();
    String getBigCategoryName();
    Long getMiddleProductCategoryId();
    String getMiddleProductCategoryName();
    Long getSmallProductCategoryId();
    String getSmallProductCategoryName();
}
