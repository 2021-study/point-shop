package product.demo.shop.domain.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.grade.entity.GradePolicyEntity;

public interface GradePolicyRepository extends JpaRepository<GradePolicyEntity, Long> {
}
