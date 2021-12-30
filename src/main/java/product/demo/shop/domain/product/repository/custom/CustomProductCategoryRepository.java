package product.demo.shop.domain.product.repository.custom;

import product.demo.shop.domain.product.dto.query.SingleProductCategoryQueryDto;

public interface CustomProductCategoryRepository {
    SingleProductCategoryQueryDto findProductCategory(Long categoryId, String categoryName);
}
