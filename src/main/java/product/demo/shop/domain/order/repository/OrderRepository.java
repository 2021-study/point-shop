package product.demo.shop.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.order.entity.OrderInfoEntity;

public interface OrderRepository extends JpaRepository<OrderInfoEntity, Long> {}
