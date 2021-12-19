package product.demo.shop.domain.point.aggregate;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import product.demo.shop.domain.point.aggregate.dto.UserPointResponseDetail;
import product.demo.shop.domain.point.entity.PointEventEntity;
import product.demo.shop.domain.point.repository.PointEventEntityRepository;

@Service
@RequiredArgsConstructor
public class UserPointAggregateServiceImpl implements UserPointAggregateService {

    private final PointEventEntityRepository pointEventEntityRepository;

    @Override
    public Page<UserPointResponseDetail> searchUserPointDetails(
            long userInfoId, Pageable pageable) {
        var queryResults = pointEventEntityRepository.findByUserInfoId(userInfoId, pageable);
        var returnResult = queryResults.map(PointEventEntity::toUserPointResponseDetail);
        return returnResult;
    }
}
