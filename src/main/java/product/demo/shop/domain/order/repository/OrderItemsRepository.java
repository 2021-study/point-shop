package product.demo.shop.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product.demo.shop.domain.order.entity.OrderItemsEntity;

public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, Long> {}
