package product.demo.shop.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
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

    @Nested
    class 비밀번호_암호화_테스트{
        @Test
        void 디폴트_bcrypt_암호화_테스트(){
            String planPassword = "1234asdf!@#$";
            String encodedPassword = passwordEncoder.encode(planPassword);


            assertThat(planPassword, not(equalTo(encodedPassword)));
            assertThat(encodedPassword, startsWith("{bcrypt}"));
            Assertions.assertDoesNotThrow(() -> {
                passwordEncoder.matches(planPassword,encodedPassword);
            });
        }
    }


    @Nested
    class 비밀번호_매칭_테스트{
        @Test
        void bcrypt로_암호화된_비밀번호_매칭_테스트(){
            String encodedPassword = "{bcrypt}$2a$10$XSRY968YOaQ0MQUYezoqQ.tes.1hn9AOhHeq3VXw9EB4.GHefq6Ee";
            String planPassword = "1234asdf!@#$";
            assertThat(passwordEncoder.matches(planPassword,encodedPassword), is(equalTo(true)));
        }

        @Test
        void 암호화_알고리즘_prefix가_없는_비밀번호는_bcrypt로_매칭되는지_테스트(){
            String encodedPassword = "$2a$10$XSRY968YOaQ0MQUYezoqQ.tes.1hn9AOhHeq3VXw9EB4.GHefq6Ee";
            String planPassword = "1234asdf!@#$";
            assertThat(passwordEncoder.matches(planPassword,encodedPassword), is(equalTo(true)));
        }
    }


}