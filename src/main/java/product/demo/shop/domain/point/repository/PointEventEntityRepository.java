package product.demo.shop.domain.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.point.entity.PointEventEntity;

public interface PointEventEntityRepository extends JpaRepository<PointEventEntity, Long> {
}
