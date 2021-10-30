package product.demo.shop.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.repository.custom.CustomUserRepository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {
}
