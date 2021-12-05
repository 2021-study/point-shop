package product.demo.shop.common.cache;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import product.demo.shop.configuration.CacheConfiguration;

@Slf4j
@Service
public class CaffeineLocalCacheTestService {

    @Cacheable(cacheNames={"PRODUCTS"},
            cacheManager = CacheConfiguration.LOCAL_CAFFEINE_CACHE_MANAGER,
            key = "'productId-'.concat(#product.productId)"
    )
    public TestProduct getWithCache(TestProduct product) throws InterruptedException {
        Thread.sleep(500);
        log.info("====> getWithCache : " + product.getProductId());
        return TestProduct.sampleData();
    }

    @CachePut(
            cacheNames = {"PRODUCTS"},
            cacheManager = CacheConfiguration.LOCAL_CAFFEINE_CACHE_MANAGER,
            key = "'productId-'.concat(#product.productId)"
    )
    public TestProduct putWithCache(TestProduct product) throws InterruptedException {
        Thread.sleep(500);
        log.info("====> putWithCache : " + product.getProductId());
        return TestProduct.sampleData();
    }

    @CacheEvict(
            cacheNames = {"PRODUCTS"},
            cacheManager = CacheConfiguration.LOCAL_CAFFEINE_CACHE_MANAGER,
            key = "'productId-'.concat(#testProduct.productId)"
    )
    public TestProduct evictWithCache(TestProduct testProduct) throws InterruptedException {
        Thread.sleep(500);
        log.info("====> evictWithCache : " + testProduct.getProductId());
        return TestProduct.sampleData();
    }
}
