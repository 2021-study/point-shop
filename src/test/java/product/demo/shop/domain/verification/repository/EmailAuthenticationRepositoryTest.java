package product.demo.shop.domain.verification.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import product.demo.shop.common.entity.AccountAuditAware;
import product.demo.shop.configuration.DataBaseConfiguration;
import product.demo.shop.configuration.QuerydslConfig;
import product.demo.shop.domain.verification.entity.EmailVerificationEntity;
import product.demo.shop.domain.verification.enums.VerificationCodeStatus;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@DataJpaTest(
        excludeAutoConfiguration = FlywayAutoConfiguration.class,
        includeFilters =
        @ComponentScan.Filter(
                type = ASSIGNABLE_TYPE,
                classes = {
                        AccountAuditAware.class,
                        DataBaseConfiguration.class,
                        QuerydslConfig.class
                }))
class EmailAuthenticationRepositoryTest {
    @Autowired
    EmailAuthenticationRepository emailAuthenticationRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUpData(){

        emailAuthenticationRepository.saveAll(
                Arrays.asList(
                        makeEmailVerificationEntitySample(1L, "4", getPastTime(), VerificationCodeStatus.CREATED),
                        makeEmailVerificationEntitySample(2L, "6", getPastTime(), VerificationCodeStatus.CONFIRMED),
                        makeEmailVerificationEntitySample(1L, "2", getPastTime(), VerificationCodeStatus.CONFIRMED),
                        makeEmailVerificationEntitySample(4L, "3", getFutureTime(), VerificationCodeStatus.CREATED),
                        makeEmailVerificationEntitySample(1L, "5", getFutureTime(), VerificationCodeStatus.CREATED),
                        makeEmailVerificationEntitySample(2L, "1", getFutureTime(), VerificationCodeStatus.CREATED),
                        makeEmailVerificationEntitySample(3L, "7", getFutureTime(), VerificationCodeStatus.CREATED)
                )
        );

        em.flush();
        em.clear();

    }

    private LocalDateTime getFutureTime() {
        return LocalDateTime.now().plus(1L, ChronoUnit.DAYS);
    }

    private LocalDateTime getPastTime() {
        return LocalDateTime.now().minus(1L, ChronoUnit.DAYS);
    }

    private EmailVerificationEntity makeEmailVerificationEntitySample(long userId, String verificationCode, LocalDateTime expiredDate, VerificationCodeStatus verificationCodeStatus) {
        return EmailVerificationEntity.builder()
                .userId(userId)
                .verificationCode(verificationCode)
                .expiredDate(expiredDate)
                .verificationCodeStatus(verificationCodeStatus)
                .build();
    }

    @Nested
    class 이메일_인증_코드로_인증_엔티티_가져오기{
        @Test
        void 이메일_인증_코드가_존재하는_인증코드인_경우(){
            Optional<EmailVerificationEntity> findEmailVerificationEntity = emailAuthenticationRepository.findByVerificationCode("3");

            assertThat(findEmailVerificationEntity.isPresent(),is(equalTo(true)));
            assertThat(findEmailVerificationEntity.get().getVerificationCode(),is(equalTo("3")));
        }

        @Test
        void 이메일_인증_코드가_존재하지_않는_인증코드인_경우(){
            Optional<EmailVerificationEntity> findEmailVerificationEntity = emailAuthenticationRepository.findByVerificationCode("999");

            assertThat(findEmailVerificationEntity.isEmpty(),is(equalTo(true)));
        }
    }

}