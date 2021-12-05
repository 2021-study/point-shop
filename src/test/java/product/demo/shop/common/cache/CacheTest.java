package product.demo.shop.common.cache;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CacheTest {

    @Autowired
    public CaffeineLocalCacheTestService testService;

    @Test
    @Order(1)
    public void returnCacheData() throws Exception {
        var testProduct = TestProduct.sampleData();
        log.info("====> testProduct : " + testProduct.getProductId());

        var firstResponse = testService.getWithCache(testProduct);
        var secondResponse = testService.getWithCache(testProduct);


        log.info("====> firstResponse : " + firstResponse.getProductId());
        log.info("====> secondResponse : " + secondResponse.getProductId());
        assertThat(firstResponse.getProductId()).isEqualTo(secondResponse.getProductId());
    }

    @Test
    @Order(2)
    public void putAndGetData() throws Exception {
        var testProduct = TestProduct.sampleData();

        var firstResponse = testService.putWithCache(testProduct);
        var secondResponse = testService.getWithCache(testProduct);

        log.info("====> testProduct : " + testProduct.getProductId());
        log.info("====> firstResponse : " + firstResponse.getProductId());
        log.info("====> secondResponse : " + secondResponse.getProductId());
        assertThat(firstResponse.getProductId()).isEqualTo(secondResponse.getProductId());
    }

    @Test
    @Order(3)
    public void removeDataCache() throws Exception {
        var testProduct = TestProduct.sampleData();
        var firstResponse = testService.putWithCache(testProduct);
        var secondResponse = testService.evictWithCache(testProduct);
        var thirdResponse = testService.getWithCache(testProduct);

        assertThat(firstResponse.getProductId()).isNotEqualTo(secondResponse.getProductId());
        assertThat(firstResponse.getProductId()).isNotEqualTo(thirdResponse.getProductId());
    }
}
