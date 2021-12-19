package product.demo.shop.domain.point.aggregate.dto;

import product.demo.shop.domain.point.entity.enums.PointEventType;

import java.time.LocalDateTime;

public record UserPointResponseDetail(
        Long pointEventId,
        PointEventType eventType,
        int point,
        LocalDateTime pointExpiredDate
) {

}
