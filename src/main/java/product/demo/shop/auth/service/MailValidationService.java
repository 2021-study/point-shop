package product.demo.shop.auth.service;

import product.demo.shop.auth.dto.MailValidationDto;
import product.demo.shop.auth.dto.requests.MailValidationRequest;

public interface MailValidationService {
    MailValidationDto makeMailValidation(MailValidationRequest validationRequest);

    MailValidationDto validateMailCode(Long userInfoId, String tokenString);
}
