package product.demo.shop.domain.grade.testcreator;

import java.util.List;
import product.demo.shop.domain.product.entity.ProductCategoryEntity;
import product.demo.shop.domain.product.repository.ProductCategoryRepository;

public class ProductCategoryCreator {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryCreator(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public void setUp() {
        productCategoryRepository.deleteAll();
        setUpTestData();
    }

    private void setUpTestData() {
        var productCategoryList = List.of(
                ProductCategoryEntity.of(1,1,"농산물"),
                ProductCategoryEntity.of(2,1,"채소"),
                ProductCategoryEntity.of(3,1,"과일"),
                ProductCategoryEntity.of(4,1,"기타"),
                ProductCategoryEntity.of(5,2,"당근"),
                ProductCategoryEntity.of(6,3,"사과"),
                ProductCategoryEntity.of(7,7,"유제품"),
                ProductCategoryEntity.of(8,7,"가공유"),
                ProductCategoryEntity.of(9,8,"초코우유")
        );

        productCategoryRepository.saveAllAndFlush(productCategoryList);
    }
}
