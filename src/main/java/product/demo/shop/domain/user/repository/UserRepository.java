package product.demo.shop.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.entity.enums.UserStatusType;
import product.demo.shop.domain.user.repository.custom.CustomUserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {
    boolean existsByUserAccountId(String userAccountId);
    //    Optional<UserEntity> findByUserStatusAndUserAccountId(UserStatusType userStatus, String
    // userAccountId);
    Optional<UserEntity> findByUserAccountId(
            String userAccountId); // userAccountId 자체가 Unique 하다고 가정.
}
