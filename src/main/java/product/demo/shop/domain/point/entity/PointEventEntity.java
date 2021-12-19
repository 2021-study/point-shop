package product.demo.shop.domain.point.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import product.demo.shop.common.entity.AuditEntity;
import product.demo.shop.domain.point.aggregate.dto.UserPointResponseDetail;
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
@Table(name = "TB_POINT_EVENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PointEventEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointEventId;

    @Column private long userInfoId;

    @Column
    @Enumerated(EnumType.STRING)
    private PointEventType eventType;

    @Column private int point;

    @Column private String code;

    @Column private String codeId;

    @Column private String detail;

    @Column private LocalDateTime pointExpiredDate;

    public static PointEventEntity of(
            long userInfoId,
            PointEventType eventType,
            int point,
            String code,
            String codeId,
            String detail,
            LocalDateTime pointExpiredDate) {
        return new PointEventEntity(
                null, userInfoId, eventType, point, code, codeId, detail, pointExpiredDate);
    }

    public UserPointResponseDetail toUserPointResponseDetail() {
        return new UserPointResponseDetail(
                this.pointEventId, this.eventType, this.point, this.pointExpiredDate);
    }
}
