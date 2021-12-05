package product.demo.shop.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.domain.user.repository.UserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ActiveProfiles("local")
@Disabled // 로컬에서 테스트시 해당 부분 주석 처리하시면 됩니다.
public class CryptoConverterTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("(로컬 전용) User에 대해서 양방향 암호화 되는 부분들은 자동으로 encryption/decription 되어야 한다.")
    public void aesConverterTest()  {
        userRepository.findById(1L).ifPresent(it->{
            try {
                System.out.println("IT : " + objectMapper.writeValueAsString(it));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            assertThat(it.getEmail().contains("@")).isTrue();
        });
    }
}
