package product.demo.shop.domain.email.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import product.demo.shop.domain.email.entity.EmailVerificationEntity;

import java.util.Optional;

public interface EmailAuthenticationRepository extends CrudRepository<EmailVerificationEntity,Long> {
    Optional<EmailVerificationEntity> findByVerificationCode(String verificationCode);
}
