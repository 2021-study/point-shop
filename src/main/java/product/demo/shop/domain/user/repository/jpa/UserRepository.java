package product.demo.shop.domain.user.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.repository.custom.CustomUserRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {
}
