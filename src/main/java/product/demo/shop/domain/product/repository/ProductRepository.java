package product.demo.shop.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.product.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {}
