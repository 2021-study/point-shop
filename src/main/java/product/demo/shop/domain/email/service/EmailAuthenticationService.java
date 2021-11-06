package product.demo.shop.domain.email.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import product.demo.shop.domain.email.entity.EmailVerificationEntity;
import product.demo.shop.domain.email.repository.EmailAuthenticationRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailAuthenticationService {
    private final EmailAuthenticationRepository emailAuthenticationRepository;
    public void verifyEmailUsingVerificationCode(String verificationCode) {
    }
}
