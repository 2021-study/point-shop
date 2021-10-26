package product.demo.shop.common.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestAsyncService {

    @Async
    public void asyncCounterDelayed() throws InterruptedException {
        log.info("======> Async TEST Start : " + SharedCounter.getValue());
        Thread.sleep(500);
        log.info("======> Async TEST END : "+ SharedCounter.incrementCounter());

    }

    @Async
    public void asyncImmediate() {
        SharedCounter.incrementCounter();
    }
}
