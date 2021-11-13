package product.demo.shop.domain.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.grade.entity.UserGradeEntity;
import product.demo.shop.domain.grade.entity.enums.GradeName;

import java.util.Optional;

public interface UserGradeRepository extends JpaRepository<UserGradeEntity, Long> {
    Optional<UserGradeEntity> findByGradeName(GradeName gradeName);
}
