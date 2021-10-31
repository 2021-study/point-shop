package product.demo.shop.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = PasswordEncoderConfig.class)
class PasswordEncoderConfigTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void 비밀번호_암호화_테스트(){
        String planPassword = "1234asdf!@#$";
        String encodedPassword = passwordEncoder.encode(planPassword);

        assertThat(planPassword, not(equalTo(encodedPassword)));
        Assertions.assertDoesNotThrow(() -> {
            passwordEncoder.matches(planPassword,encodedPassword);
        });
    }

    @Test
    void 비밀번호_매칭_테스트(){
        String encodedPassword = "$2a$10$XSRY968YOaQ0MQUYezoqQ.tes.1hn9AOhHeq3VXw9EB4.GHefq6Ee";
        String planPassword = "1234asdf!@#$";
        assertThat(passwordEncoder.matches(planPassword,encodedPassword), is(equalTo(true)));
    }
}