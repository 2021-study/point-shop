/**
 * ===============================================================
 * File name : JwtFilter.java
 * Created by injeahwang on 2021-07-11
 * ===============================================================
 */
package product.demo.shop.domain.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import product.demo.shop.domain.auth.exception.JwtErrorCode;

public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, RuntimeException{
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        try{
            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Security context에 '{}' 인증 정보를 저장했습니다, uri :  '{}'", authentication.getName(), requestURI);
            } else {
                logger.debug("유효한 JWT 토큰이 없습니다. uri : {}", requestURI);
            }
        } catch(SecurityException | MalformedJwtException e){
            logger.error("잘못된 JWT 서명입니다.");
            httpServletRequest.setAttribute("exception", JwtErrorCode.JWT_INVALID.getValue());
        } catch(ExpiredJwtException e) {
            logger.error("만료된 JWT 토큰입니다.");
            httpServletRequest.setAttribute("exception", JwtErrorCode.JWT_EXPIRED.getValue());
        } catch(UnsupportedJwtException e) {
            logger.error("지원되지 않는 JWT토큰입니다.");
        } catch(IllegalArgumentException e) {
            logger.error("JWT 토큰이 잘못되었습니다.");
        }catch(Exception e){
            logger.error(e.getMessage());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
