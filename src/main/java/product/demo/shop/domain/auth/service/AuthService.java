package product.demo.shop.domain.auth.service;

import product.demo.shop.domain.auth.dto.MailValidationDto;
import product.demo.shop.domain.auth.dto.responses.SignUpCompleteResponse;
import product.demo.shop.domain.auth.dto.SignupDto;

public interface AuthService {

    MailValidationDto newUserSignUp(SignupDto signupDto);
    SignUpCompleteResponse completeSignUp(Long userInfoId, String tokenString);
}
