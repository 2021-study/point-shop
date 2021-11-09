package product.demo.shop.domain.verification.repository;


import org.springframework.data.repository.CrudRepository;
import product.demo.shop.domain.verification.entity.EmailVerificationEntity;

import java.util.Optional;

public interface EmailVerificationRepository extends CrudRepository<EmailVerificationEntity,Long> {
    Optional<EmailVerificationEntity> findByVerificationCode(String verificationCode);
}
