package product.demo.shop.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import product.demo.shop.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements ErrorCode {
    JWT_EXPIRED(HttpStatus.FORBIDDEN,"만료된 JWT 토큰입니다."),  // JWT token 만료
    JWT_INVALID(HttpStatus.BAD_REQUEST,"잘못된 JWT 서명입니다."); // JWT token 획득 실패

    private HttpStatus errorStatus;
    private String errorMessage;

}
