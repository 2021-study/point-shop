package product.demo.shop.common.cache;

import java.math.BigDecimal;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TestProduct {
    private long productId;
    private String productCategoryBigCode;
    private String productCategoryMiddleCode;
    private String productCategorySmallCode;
    private BigDecimal price;

    public static TestProduct sampleData() {
        return new TestProduct(new Random().nextLong()/100L, "001", "000001", "0000001", BigDecimal.TEN);
    }
}
