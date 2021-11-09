package product.demo.shop.domain.verification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product.demo.shop.domain.verification.entity.EmailVerificationEntity;
import product.demo.shop.domain.verification.enums.VerificationCodeStatus;
import product.demo.shop.domain.verification.exception.EmailVerificationErrorCode;
import product.demo.shop.domain.verification.exception.EmailVerificationException;
import product.demo.shop.domain.verification.repository.EmailAuthenticationRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class EmailAuthenticationService {
    private final EmailAuthenticationRepository emailAuthenticationRepository;
    private final int ADD_EXPIRED_HOURS = 3;

    public void verifyEmailUsingVerificationCode(String verificationCode) {
        EmailVerificationEntity findEmailVerificationEntity = emailAuthenticationRepository.findByVerificationCode(verificationCode).orElseThrow(() -> new EmailVerificationException(EmailVerificationErrorCode.NOT_FOUND_TOKEN));

        boolean isBefore = findEmailVerificationEntity.getExpiredDate().isBefore(LocalDateTime.now());
        if(isBefore){
            throw new EmailVerificationException(EmailVerificationErrorCode.EXPIRED_TOKEN);
        }

        if(findEmailVerificationEntity.getVerificationCodeStatus()  == VerificationCodeStatus.CONFIRMED){
            throw new EmailVerificationException(EmailVerificationErrorCode.ALREADY_USED_TOKEN);
        }

        findEmailVerificationEntity.useCode();
    }

    public String createVerificationCode(long userInfoId) {
        EmailVerificationEntity emailVerificationEntity = EmailVerificationEntity
                .builder()
                .userId(userInfoId)
                .verificationCodeStatus(VerificationCodeStatus.CREATED)
                .expiredDate(LocalDateTime.now().plus(ADD_EXPIRED_HOURS, ChronoUnit.HOURS))
                .verificationCode(UUID.randomUUID().toString())
                .build();

        emailAuthenticationRepository.save(emailVerificationEntity);

        return emailVerificationEntity.getVerificationCode();
    }

}
