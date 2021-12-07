package product.demo.shop.jwt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
public class TokenExceptionTest {

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @Autowired UserRepository userRepository;

    @MockBean TokenProvider tokenProvider;

    @Autowired private PasswordEncoder delegatingPasswordEncoder;

    @BeforeAll
    public void setUp() {
        var testUser = UserEntity.builder()
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
        given(tokenProvider.createToken(any())).willReturn("testToken");

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

        // JWT Token 검증시 무조건 만료된 토큰 결과가 나오도록 Mocking
        given(tokenProvider.validateToken(any())).willThrow(ExpiredJwtException.class);
        mockMvc.perform(
                        get("/api/employee/list")
                                .header("authorization", "Bearer " + tokenDto.token()))
                .andDo(print())
                .andExpect(status().isForbidden()) // 토큰 만료시 HTTP 403 Forbidden 을 리턴한다.
                .andExpect(jsonPath(("$.errorMessage")).exists())
                .andExpect(jsonPath(("$.errorMessage")).value("만료된 JWT 토큰입니다."));
    }

    @Test
    public void tokenTimeIllegalArgumentException() throws Exception {
        given(tokenProvider.createToken(any())).willReturn("testToken");

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

        given(tokenProvider.validateToken(any())).willThrow(MalformedJwtException.class);

        mockMvc.perform(
                        get("/api/employee/list")
                                .header("authorization", "Bearer " + tokenDto.token()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(("$.errorMessage")).exists())
                .andExpect(jsonPath(("$.errorMessage")).value("잘못된 JWT 서명입니다."));
    }
}
