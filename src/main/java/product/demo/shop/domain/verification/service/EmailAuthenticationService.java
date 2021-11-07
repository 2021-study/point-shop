package product.demo.shop.domain.verification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import product.demo.shop.domain.verification.repository.EmailAuthenticationRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailAuthenticationService {
    private final EmailAuthenticationRepository emailAuthenticationRepository;
    public void verifyEmailUsingVerificationCode(String verificationCode) {
    }

    public String createVerificationCode(long user_seq) {
        return null;
    }
}
