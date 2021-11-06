package product.demo.shop.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.demo.shop.domain.auth.dto.responses.SignUpCompleteResponse;
import product.demo.shop.domain.auth.service.AuthService;
import product.demo.shop.domain.auth.service.MailValidationService;
import product.demo.shop.domain.auth.dto.MailValidationDto;
import product.demo.shop.domain.auth.dto.SignupDto;
import product.demo.shop.domain.auth.dto.requests.SignupRequest;
import product.demo.shop.domain.auth.dto.responses.SignupResponse;

import static product.demo.shop.domain.auth.controller.AuthController.AUTH_API_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH_API_PATH)
public class AuthController {
    public static final String AUTH_API_PATH ="/api/v1/auth";

    private final AuthService authService;
    private final MailValidationService mailValidationService;

    @PostMapping(path = "/sign-up")
    public ResponseEntity<SignupResponse> signUp(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(this.authService.newUserSignUp(SignupDto.toSignupDto(signupRequest)).toSignupResponse("SUCCESS"));
    }

    @GetMapping(path = "/verify/{userInfoId}/{tokenValue}")
    public ResponseEntity<SignUpCompleteResponse> validationNewUser(
            @PathVariable Long userInfoId,
            @PathVariable String tokenValue){
        return ResponseEntity.ok(this.authService.completeSignUp(userInfoId, tokenValue));
    }

//    @PostMapping(path="/stand-alone-sign-in")
//    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
//
//        return ResponseEntity.ok(SignInResponse.class);
//    }
}
