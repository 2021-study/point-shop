package product.demo.shop.auth.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.demo.shop.auth.dto.TokenDto;
import product.demo.shop.auth.exception.PointShopAuthErrorCode;
import product.demo.shop.auth.exception.PointShopAuthException;
import product.demo.shop.jwt.JwtFilter;
import product.demo.shop.jwt.TokenProvider;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LoginController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(
            @Valid @RequestBody LoginRequest loginDto) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.userId(), loginDto.password());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.createToken(authentication);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            throw new PointShopAuthException(PointShopAuthErrorCode.LOGIN_PASSWORD_ERROR, ex);
        } catch (UsernameNotFoundException ex) {
            throw new PointShopAuthException(PointShopAuthErrorCode.LOGIN_USER_NOT_EXISTS, ex);
        } catch (Exception e) {
            throw e;
        }

    }
}

record LoginRequest(
        @NotNull
        String userId,
        @NotNull
        String password
){}