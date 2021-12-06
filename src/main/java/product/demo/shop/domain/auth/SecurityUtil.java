/**
 * ===============================================================
 * File name : SecurityUtil.java
 * Created by injeahwang on 2021-07-11
 * ===============================================================
 */
package product.demo.shop.domain.auth;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil(){}

    public static Optional<String> getCurrentUsername(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) {
            logger.debug("Security Context에 인증정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        }else if(authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}
