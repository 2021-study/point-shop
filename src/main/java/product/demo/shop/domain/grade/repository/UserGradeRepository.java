package product.demo.shop.domain.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.grade.entity.UserGradeEntity;

public interface UserGradeRepository extends JpaRepository<UserGradeEntity, Long> {
}
