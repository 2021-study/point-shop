package product.demo.shop.domain.user.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.user.entity.EmailVerificationEntity;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Long> {
    Optional<EmailVerificationEntity> findByUserIdAndVerificationCode(Long userId, String verificationCode);
}
