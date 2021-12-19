package product.demo.shop.domain.point.aggregate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import product.demo.shop.domain.point.aggregate.dto.UserPointResponseDetail;

public interface UserPointAggregateService {
    Page<UserPointResponseDetail> searchUserPointDetails(long userInfoId, Pageable pageable);
}
