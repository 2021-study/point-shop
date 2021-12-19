package product.demo.shop.domain.point.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.point.entity.PointEventEntity;

public interface PointEventEntityRepository extends JpaRepository<PointEventEntity, Long> {

    Page<PointEventEntity> findByUserInfoId(long userInfoId, Pageable pageable);
}
