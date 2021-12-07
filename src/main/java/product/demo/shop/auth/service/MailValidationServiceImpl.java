package product.demo.shop.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import product.demo.shop.common.mail.EmailParameter;
import product.demo.shop.common.mail.MailService;
import product.demo.shop.auth.dto.MailValidationDto;
import product.demo.shop.auth.dto.requests.MailValidationRequest;
import product.demo.shop.auth.exception.PointShopAuthErrorCode;
import product.demo.shop.auth.exception.PointShopAuthException;
import product.demo.shop.domain.user.entity.EmailVerificationEntity;
import product.demo.shop.domain.user.repository.jpa.EmailVerificationRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MailValidationServiceImpl implements MailValidationService {

    private final MailService mailService;
    private final EmailVerificationRepository emailVerificationRepository;

    @Override
    public MailValidationDto makeMailValidation(MailValidationRequest validationRequest) {
        try {
            var mailDto = MailValidationDto.makeValidationCodeFromMailValidRequest(validationRequest);

            var savedEmail =
                    emailVerificationRepository.save(
                            EmailVerificationEntity.fromMailValidationDto(mailDto, 180));

            mailDto.setEmailVerificationEntityId(savedEmail.getEmailVerificationCodeId());

            var emailParameter =
                    EmailParameter.builder()
                            .receiverEmailAddress(mailDto.getEmail())
                            .title("로그인 검증 메일입니다.")
                            .content(
                                    "<p>아래 URL을 클릭하여 인증을 완료해주세요</p>"
                                            + mailDto.getMailValidationUrl())
                            .build();

            // TODO : mailSend는 별도 스레드에서 비동기로 동작하기 때문에 완료 여부를 확인할 수 없음.
            // 만약 완료 여부 확인이 필요하다면 Blocking을 적용하거나,
            // 메일 전송 이력을 관리하는 Storage가 필요함.
            mailService.sendMail(emailParameter);

            return mailDto;
        } catch (Exception ex) {
            throw new PointShopAuthException(
                    PointShopAuthErrorCode.MAIL_VERIFICATION_ERROR, ex); // 귀찮아서 별도의 익셉션을 만들지 않앗습니다.
        }
    }

    @Override
    public MailValidationDto validateMailCode(Long userInfoId, String tokenString) {

        try {
            var searchedEmail =
                    this.emailVerificationRepository
                            .findByUserIdAndVerificationCode(userInfoId, tokenString)
                            .orElseThrow();

            if (searchedEmail.getExpiredDate().isBefore(LocalDateTime.now())) {
                throw new PointShopAuthException(
                        PointShopAuthErrorCode.MAIL_VERIFICATION_CODE_EXPIRED); // 만료 에러
            }

            searchedEmail.changeVerifyCodeToConfirmed("CONFIRMED");
            var updatedEntity = this.emailVerificationRepository.save(searchedEmail);

            return MailValidationDto.builder()
                    .emailVerificationEntityId(updatedEntity.getEmailVerificationCodeId())
                    .userInfoId(updatedEntity.getUserId())
                    .validationStatus(updatedEntity.getVerificationCodeStatus())
                    .build();

        } catch (PointShopAuthException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new PointShopAuthException(PointShopAuthErrorCode.MAIL_VERIFICATION_ERROR, ex);
        }
    }
}
