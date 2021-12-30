package product.demo.shop.domain.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategoryQueryDto implements MultipleCategoryQueryDtoInterface {

    private Long bigProductCategoryId;
    private String bigCategoryName;
    private Long middleProductCategoryId;
    private String middleProductCategoryName;
    private Long smallProductCategoryId;
    private String smallProductCategoryName;

    @QueryProjection
    public ProductCategoryQueryDto(
            Long bigProductCategoryId,
            String bigCategoryName,
            Long middleProductCategoryId,
            String middleProductCategoryName,
            Long smallProductCategoryId,
            String smallProductCategoryName
    ) {
        this.bigProductCategoryId = bigProductCategoryId;
        this.bigCategoryName = bigCategoryName;
        this.middleProductCategoryId = middleProductCategoryId;
        this.middleProductCategoryName = middleProductCategoryName;
        this.smallProductCategoryId = smallProductCategoryId;
        this.smallProductCategoryName = smallProductCategoryName;
    }

}
