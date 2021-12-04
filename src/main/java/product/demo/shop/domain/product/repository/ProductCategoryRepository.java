package product.demo.shop.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.product.entity.ProductCategoryEntity;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {}
