package product.demo.shop.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import product.demo.shop.auth.dto.MailValidationDto;
import product.demo.shop.auth.dto.SignupDto;
import product.demo.shop.auth.dto.requests.MailValidationRequest;
import product.demo.shop.auth.dto.responses.SignUpCompleteResponse;
import product.demo.shop.auth.exception.PointShopAuthErrorCode;
import product.demo.shop.auth.exception.PointShopAuthException;
import product.demo.shop.domain.grade.entity.UserGradeEntity;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.grade.repository.UserGradeRepository;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    //    private final UserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final UserGradeRepository userGradeRepository;
    private final MailValidationService mailValidationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MailValidationDto newUserSignUp(SignupDto signupDto) {
        var exist = this.userRepository.existsByUserAccountId(signupDto.getUserAccountId());

        if (exist) {
            throw new PointShopAuthException(PointShopAuthErrorCode.EXIST_USER);
        }

        var userGrade =
                this.userGradeRepository
                        .findByGradeName(GradeName.BRONZE)
                        .orElse(
                                UserGradeEntity.builder()
                                        .userGradeId(1L) // 없으면 걍 1로
                                        .gradeName(GradeName.BRONZE)
                                        .build());

        var savedUser =
                this.userRepository.save(
                        UserEntity.fromUserDtoWithStandAlone(
                                signupDto, userGrade, passwordEncoder));

        return mailValidationService.makeMailValidation(
                MailValidationRequest.of(signupDto.getEmail(), savedUser.getUserInfoId()));
    }

    @Override
    public SignUpCompleteResponse completeSignUp(Long userInfoId, String tokenString) {
        try {
            var validateResult =
                    this.mailValidationService.validateMailCode(userInfoId, tokenString);

            if (StringUtils.hasText(validateResult.getValidationStatus())
                    && validateResult.getValidationStatus().equals("CONFIRMED")) {
                var findUser = this.userRepository.findById(userInfoId).orElseThrow();
                findUser.changeUserStatusConfirmed();
                this.userRepository.save(findUser);
                return SignUpCompleteResponse.of(
                        findUser.getUserAccountId(), findUser.getUserStatus());
            } else {
                // 예외 처리가 귀찮아서 이렇게 둡니다.
                // 굳이 예외로 빼지 않고 디자인 해도 됩니다.
                throw new PointShopAuthException(PointShopAuthErrorCode.MAIL_VERIFICATION_ERROR);
            }
        } catch (PointShopAuthException px) {
            throw px;
        } catch (RuntimeException ex) {
            throw new PointShopAuthException(PointShopAuthErrorCode.SIGNUP_COMPLETE_ERROR, ex);
        }
    }
}
