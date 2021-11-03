package product.demo.shop.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import product.demo.shop.domain.user.service.CustomUserDetailsService;


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
            .authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .csrf()
            .and()
                .formLogin().permitAll()
            .and()
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
