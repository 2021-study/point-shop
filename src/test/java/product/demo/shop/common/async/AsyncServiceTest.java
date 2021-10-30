package product.demo.shop.common.async;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class AsyncServiceTest {

    @Autowired
    TestAsyncService testAsyncService;

    @BeforeEach
    public void initCounter(){
        SharedCounter.initCounter();
    }

    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("비동기 테스트 - (정확도 향상을 위해) 5번 반복시 마다 동일한 결과가 나와야 한다.")
    public void asyncCounterTest() throws InterruptedException {
        assertEquals(0, SharedCounter.getValue());

        log.info("======> Outer Task ::::::");
        testAsyncService.asyncCounterDelayed();
        assertEquals(0, SharedCounter.getValue());
        testAsyncService.asyncCounterDelayed();
        assertEquals(0, SharedCounter.getValue());
        testAsyncService.asyncCounterDelayed();
        assertEquals(0, SharedCounter.getValue());

        Thread.sleep(1_000);
        assertEquals(3, SharedCounter.getValue());

        SharedCounter.incrementCounter();
        assertEquals(4, SharedCounter.getValue());
    }
}
