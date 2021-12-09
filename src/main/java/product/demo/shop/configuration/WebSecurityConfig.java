package product.demo.shop.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import product.demo.shop.jwt.JwtAuthenticationEntryPoint;
import product.demo.shop.jwt.TokenProvider;
import product.demo.shop.auth.service.CustomUserDetailsService;
import product.demo.shop.healthcheck.HealthCheckController;

import static product.demo.shop.auth.controller.AuthController.AUTH_API_PATH;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/rest-docs/**") // Spring RestDoc 문서 조회
                .permitAll()
                .antMatchers(HealthCheckController.PING_PATH)
                .permitAll()
                .antMatchers(AUTH_API_PATH + "/sign-up")
                .permitAll()
                .antMatchers(AUTH_API_PATH + "/verify/{userInfoId}/{tokenValue}")
                .permitAll()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/oauth-login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .and()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .headers()
                .addHeaderWriter(
                        new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'"))
                .frameOptions()
                .disable()
                .and();
    }

    @Bean
    @Primary
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        // https://blog.devbong.com/95
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setHideUserNotFoundExceptions(false); // Not recommend
        return provider;
    }
}
