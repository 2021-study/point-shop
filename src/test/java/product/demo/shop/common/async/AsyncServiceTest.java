package product.demo.shop.common.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

         while(true){
//             log.info("====JUST WAITING====");
            // Thread 잠금 없이 3개의 서브 태스크가 모두 isDone()이 떨어지면 success
             if(future1.isDone() && future2.isDone() && future3.isDone()){
                 log.info("It's All Done");
                 assertEquals(3, SharedCounter.getValue());
                 SharedCounter.incrementCounter();
                 assertEquals(4, SharedCounter.getValue());
                 break;
             }

             // 60초 동안 서브 태스크에서 isDone() 콜백이 없으면 Timeout으로 간주
             // 해당 부분은 AsyncConfig 설정으로 들어가 있음.(executor.setAwaitTerminationSeconds(60))
         }
    }
}
