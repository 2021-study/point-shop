package product.demo.shop.domain.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleProductCategoryQueryDto {

    private Long productCategoryId;
    private Long parent;
    private String productCategoryName;

    @QueryProjection
    public SingleProductCategoryQueryDto(
            Long productCategoryId,
            Long parent,
            String productCategoryName
    ) {
        this.productCategoryId = productCategoryId;
        this.parent = parent;
        this.productCategoryName = productCategoryName;
    }
}
