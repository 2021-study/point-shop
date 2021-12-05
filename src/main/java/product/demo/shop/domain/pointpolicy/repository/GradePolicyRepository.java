package product.demo.shop.domain.pointpolicy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.pointpolicy.entity.GradePolicyEntity;

public interface GradePolicyRepository extends JpaRepository<GradePolicyEntity, Long>, CustomGradePolicyRepository {

}
