/**
 * ===============================================================
 * File name : JwtFilter.java
 * Created by injeahwang on 2021-07-11
 * ===============================================================
 */
package product.demo.shop.jwt;

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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import product.demo.shop.auth.exception.JwtErrorCode;
import product.demo.shop.auth.exception.JwtValidationException;

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
                logger.debug("Security context??? '{}' ?????? ????????? ??????????????????, uri :  '{}'", authentication.getName(), requestURI);
            } else {
                logger.debug("????????? JWT ????????? ????????????. uri : {}", requestURI);
            }
        } catch(SecurityException | MalformedJwtException e){
            logger.error("????????? JWT ???????????????.");
            httpServletRequest.setAttribute("exception",new JwtValidationException(JwtErrorCode.JWT_INVALID));
        } catch(ExpiredJwtException e) {
            logger.error("????????? JWT ???????????????.");
            httpServletRequest.setAttribute("exception",new JwtValidationException(JwtErrorCode.JWT_EXPIRED));
        } catch(UnsupportedJwtException e) {
            logger.error("???????????? ?????? JWT???????????????.");
        } catch(IllegalArgumentException e) {
            logger.error("JWT ????????? ?????????????????????.");
        }
        catch(Exception e){
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
