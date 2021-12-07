package product.demo.shop.jwt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static product.demo.shop.CommonController.DEFAULT_PATH;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import product.demo.shop.auth.dto.TokenDto;
import product.demo.shop.configuration.EnableMockMvc;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.entity.enums.UserStatusType;
import product.demo.shop.domain.user.repository.UserRepository;

@SpringBootTest
@EnableMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTokenAuthenticationTest {
    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @Autowired UserRepository userRepository;

    @Autowired TokenProvider tokenProvider;

    @Autowired private PasswordEncoder delegatingPasswordEncoder;

    @BeforeAll
    public void setUp() {
        var testUser =
                UserEntity.builder()
                        .userInfoId(1L)
                        .userGradeId(1L)
                        .userAccountId("test1")
                        .name("hwang")
                        .email("dlswp113@gmail.com")
                        .phone("010-0000-0000")
                        .address("사랑시고백구행복동")
                        .password(delegatingPasswordEncoder.encode("1234"))
                        .userStatus(UserStatusType.VERIFIED) // Verified된 유저만 로그인 처리.
                        .build();

        userRepository.saveAndFlush(testUser);
    }

    @Test
    public void tokenTimeExpiredTest() throws Exception {
        ResultActions result =
                mockMvc.perform(
                                post("/login")
                                        .content(
                                                "{\n"
                                                        + "\t\"userId\":\"test1\",\n"
                                                        + "\t\"password\":\"1234\"\n"
                                                        + "}")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType("application/json;charset=UTF-8"))
                        .andExpect(jsonPath("$.token").exists());

        String str = result.andReturn().getResponse().getContentAsString();

        System.out.println("Result  Token : " + str);

        TokenDto tokenDto = objectMapper.readValue(str, TokenDto.class);

        mockMvc.perform(
                        get(DEFAULT_PATH)
                                .header("authorization", "Bearer " + tokenDto.token()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
