package product.demo.shop.common.cache;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
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
        return new TestProduct(ThreadLocalRandom.current().nextLong(10000000), "001", "000001", "0000001", BigDecimal.TEN);
    }
}
