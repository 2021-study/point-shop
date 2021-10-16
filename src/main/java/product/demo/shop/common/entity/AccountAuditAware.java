package product.demo.shop.common.entity;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AccountAuditAware implements AuditorAware<String> {

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO : Securities 설정이 fix된 후 securityContextHolder에서 current User를 빼오는 작업이 필요.
        return Optional.of(applicationName);
    }
}
