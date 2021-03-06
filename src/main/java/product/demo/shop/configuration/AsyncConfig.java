package product.demo.shop.configuration;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    private final int corePoolSize;
    private final int maxPoolSize;
    private final int queueCapacity;

    public AsyncConfig(
        @Value("${pointshop.async.core-pool-size:4}") int corePoolSize,
        @Value("${pointshop.async.max-pool-size:16}") int maxPoolSize,
        @Value("${pointshop.async.queue-capacity:512}") int queueCapacity
    ) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.queueCapacity = queueCapacity;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);

//        executor.setTaskDecorator(new ClonedTaskDecorator());
        executor.setThreadNamePrefix("async-task-");
        executor.setThreadGroupName("async-group");
        executor.setWaitForTasksToCompleteOnShutdown(true); // 작업의 유실을 막기 위해 큐에 남아있는 Task를 모두 처리 할 때 까지 shutdown 유보.
        executor.setAwaitTerminationSeconds(60); // 최대 60초 대기
        executor.initialize();
        return executor;
    }

    public static class ClonedTaskDecorator implements TaskDecorator {

        @Override
        public Runnable decorate(Runnable runnable) {
            Map<String, String> callerThreadContext = MDC.getCopyOfContextMap();
            return () -> {
                MDC.setContextMap(callerThreadContext);
                runnable.run();
            };
        }
    }
}
