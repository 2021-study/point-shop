package product.demo.shop.domain.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {
    JWT_EXPIRED("ERR_JWT_EXPIRED"),             // JWT token 만료
    JWT_INVALID("ERR_INVALID_JWT"); // JWT token 획득 실패

    private String value;
}
