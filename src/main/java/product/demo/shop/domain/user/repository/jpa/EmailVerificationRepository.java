package product.demo.shop.domain.user.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.verification.entity.EmailVerificationEntity;

public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Long> {
}
