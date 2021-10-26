package product.demo.shop.common.async;

import java.util.concurrent.atomic.AtomicInteger;

public class SharedCounter {

    public static AtomicInteger counter = new AtomicInteger();

    public static void initCounter() {
        counter.set(0);
    }
    public static int incrementCounter(){
        return counter.incrementAndGet();
    }

    public static int getValue() throws InterruptedException {
        Thread.sleep(5);
        return counter.getAcquire();
    }

}
