package product.demo.shop.domain.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.point.entity.PointDetailEntity;

public interface PointDetailRepository extends JpaRepository<PointDetailEntity, Long> {}
