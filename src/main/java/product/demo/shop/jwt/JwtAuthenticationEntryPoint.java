/**
 * =============================================================== File name :
 * JwtAuthenticationEntryPoint.java Created by injeahwang on 2021-07-11
 * ===============================================================
 */
package product.demo.shop.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import product.demo.shop.auth.exception.JwtErrorCode;
import product.demo.shop.auth.exception.JwtValidationException;
import product.demo.shop.common.exception.CommonErrorResponse;
import product.demo.shop.common.exception.CommonException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {

        if (request.getAttribute("exception") instanceof JwtValidationException) {
            JwtValidationException reqException =
                    (JwtValidationException) request.getAttribute("exception");

            response.setStatus(reqException.getErrorStatus().value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(objectMapper.writeValueAsString(CommonErrorResponse.createErrorResponse(reqException)));

        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
