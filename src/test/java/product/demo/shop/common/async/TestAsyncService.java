package product.demo.shop.common.async;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@Slf4j
public class TestAsyncService {

    @Async
    public ListenableFuture<String> asyncCounterDelayed() throws InterruptedException {
        log.info("======> Async TEST Start : " + SharedCounter.getValue());
        Thread.sleep(500);
        log.info("======> Async TEST END : "+ SharedCounter.incrementCounter());
        return new AsyncResult<>("Success");
    }

    @Async
    public void asyncImmediate() {
        SharedCounter.incrementCounter();
    }
}
