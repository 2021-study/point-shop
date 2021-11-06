package product.demo.shop.domain.auth.service;

import product.demo.shop.domain.auth.dto.MailValidationDto;
import product.demo.shop.domain.auth.dto.requests.MailValidationRequest;

public interface MailValidationService {
    MailValidationDto makeMailValidation(MailValidationRequest validationRequest);
    MailValidationDto validateMailCode(Long userInfoId, String tokenString);
}
