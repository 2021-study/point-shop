package product.demo.shop.domain.verification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.demo.shop.domain.verification.service.EmailAuthenticationService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/verify")
public class VerificationRestController {
    private final EmailAuthenticationService emailAuthenticationService;

    @GetMapping("/email/{verificationCode}")
    private String verifyEmailUsingVerificationCode(@PathVariable String verificationCode){
        emailAuthenticationService.verifyEmailUsingVerificationCode(verificationCode);
        return "";
    }

}
