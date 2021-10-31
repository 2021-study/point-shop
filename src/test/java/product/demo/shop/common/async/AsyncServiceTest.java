package product.demo.shop.common.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class AsyncServiceTest {

    @Autowired TestAsyncService testAsyncService;

    @BeforeEach
    public void initCounter() {
        SharedCounter.initCounter();
    }

    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("비동기 테스트 - (정확도 향상을 위해) 5번 반복시 마다 동일한 결과가 나와야 한다.")
    public void asyncCounterTest() throws InterruptedException {
        assertEquals(0, SharedCounter.getValue());

        log.info("======> Outer Task ::::::");
        var future1 = testAsyncService.asyncCounterDelayed();
        assertEquals(0, SharedCounter.getValue());

        var future2 = testAsyncService.asyncCounterDelayed();
        assertEquals(0, SharedCounter.getValue());

        var future3 = testAsyncService.asyncCounterDelayed();
        assertEquals(0, SharedCounter.getValue());

         future1.addCallback(
                (res) -> log.info("TASK 1 :: " + res),
                (ex) -> log.error("TASK 1 FAILED", ex));

         future2.addCallback(
                 (res) -> log.info("TASK 2 :: " + res),
                 (ex) -> log.error("TASK 2 FAILED", ex));

         future3.addCallback(
                 (res) -> log.info("TASK 3 :: " + res),
                 (ex) -> log.error("TASK 3 FAILED", ex));

         try{
             // Future<T>.get() 으로 blocking으로 비동기 태스크의 결과를 얻을 때 까지 대기할 수 있습니다.
             String task1Result = future1.get(20, TimeUnit.SECONDS);
             String task2Result = future2.get(20, TimeUnit.SECONDS);
             String task3Result = future3.get(20, TimeUnit.SECONDS);

             assertThat(task1Result).isEqualTo("Success");
             assertThat(task2Result).isEqualTo("Success");
             assertThat(task3Result).isEqualTo("Success");
         }catch(TimeoutException | ExecutionException | InterruptedException ex){
             ex.printStackTrace();
         }
    }
}
