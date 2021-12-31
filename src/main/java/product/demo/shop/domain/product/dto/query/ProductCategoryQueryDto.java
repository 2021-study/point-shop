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
    private Long bigParent;
    private String bigCategoryName;
    private Long middleProductCategoryId;
    private Long middleParent;
    private String middleProductCategoryName;
    private Long smallProductCategoryId;
    private Long smallParent;
    private String smallProductCategoryName;

    @QueryProjection
    public ProductCategoryQueryDto(
            Long bigProductCategoryId,
            Long bigParent,
            String bigCategoryName,
            Long middleProductCategoryId,
            Long middleParent,
            String middleProductCategoryName,
            Long smallProductCategoryId,
            Long smallParent,
            String smallProductCategoryName) {
        this.bigProductCategoryId = bigProductCategoryId;
        this.bigParent = bigParent;
        this.bigCategoryName = bigCategoryName;
        this.middleProductCategoryId = middleProductCategoryId;
        this.middleParent = middleParent;
        this.middleProductCategoryName = middleProductCategoryName;
        this.smallProductCategoryId = smallProductCategoryId;
        this.smallParent = smallParent;
        this.smallProductCategoryName = smallProductCategoryName;
    }
}
