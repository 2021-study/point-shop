package product.demo.shop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder delegatingPasswordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap();
        String defaultPasswordAlgorithm = "bcrypt";
        BCryptPasswordEncoder defaultPasswordEncoder = new BCryptPasswordEncoder();
        encoders.put(defaultPasswordAlgorithm, defaultPasswordEncoder);

        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(defaultPasswordAlgorithm, encoders);

        return delegatingPasswordEncoder;
    }
}
