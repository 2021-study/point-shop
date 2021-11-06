package product.demo.shop.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import product.demo.shop.CommonController;
import product.demo.shop.domain.user.service.CustomUserDetailsService;
import product.demo.shop.healthcheck.HealthCheckController;

import static product.demo.shop.domain.auth.controller.AuthController.AUTH_API_PATH;


@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
            .authorizeRequests()
                .antMatchers(HealthCheckController.PING_PATH).permitAll()
                .antMatchers(AUTH_API_PATH+"/sign-up").permitAll()
                .antMatchers("/api/v1/auth/verify/{userInfoId}/{tokenValue}").permitAll()
                .antMatchers(CommonController.DEFAULT_PATH).hasAnyRole("USER")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .permitAll()
            .and()
                .userDetailsService(customUserDetailsService)
                .oauth2Login()
            .and()
                .logout().permitAll()
            .and()
                .headers()
                    .addHeaderWriter(
                        new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'")
                    ).frameOptions().disable();
    }
}
