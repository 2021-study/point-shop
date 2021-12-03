package product.demo.shop.domain.point.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import product.demo.shop.common.entity.AuditEntity;
import product.demo.shop.domain.point.entity.enums.PointEventType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_POINT_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PointDetailEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointDetailId;

    @Column
    private long pointEventId;

    @Column
    @Enumerated(EnumType.STRING)
    private PointEventType eventType;

    @Column
    private long detailAccumulatedPointId;

    @Column
    private int point;  // 21억 안넘나..?

    @Column
    private LocalDateTime pointProcessedDate;
}
