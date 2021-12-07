package product.demo.shop.auth.service;

import product.demo.shop.auth.dto.MailValidationDto;
import product.demo.shop.auth.dto.responses.SignUpCompleteResponse;
import product.demo.shop.auth.dto.SignupDto;

public interface AuthService {

    MailValidationDto newUserSignUp(SignupDto signupDto);

    SignUpCompleteResponse completeSignUp(Long userInfoId, String tokenString);
}
