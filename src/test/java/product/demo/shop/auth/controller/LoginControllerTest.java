package product.demo.shop.auth.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
// import product.demo.shop.auth.dto.responses.SignupResponse;
import product.demo.shop.auth.dto.TokenDto;
import product.demo.shop.common.exception.CommonErrorResponse;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.entity.enums.UserStatusType;
import product.demo.shop.domain.user.repository.UserRepository;

/*실 테스트*/
@Slf4j
@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Autowired
    @Qualifier("commonObjectMapper")
    private ObjectMapper objectMapper;

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder delegatingPasswordEncoder;

    @BeforeEach
    public void setUp(
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(
                                webApplicationContext) // mock 객체가 아닌 WebApplicationContext를 셋업 합니다.
                        .addFilter(new CharacterEncodingFilter("UTF-8", true)) // REST Doc 한글 깨짐 방지.
                        .apply(documentationConfiguration(restDocumentationContextProvider))
                        .build();
    }

    @Test
    public void inHouseLoginTest() throws Exception {
        var testUserEntity =
                UserEntity.builder()
                        .userInfoId(1L)
                        .userGradeId(1L)
                        .userAccountId("jay")
                        .name("hwang")
                        .email("dlswp113@gmail.com")
                        .phone("010-0000-0000")
                        .address("사랑시고백구행복동")
                        .password(delegatingPasswordEncoder.encode("1234"))
                        .userStatus(UserStatusType.VERIFIED) // Verified된 유저만 로그인 처리.
                        .build();

        userRepository.saveAndFlush(testUserEntity);

        var testLoginRequest = new LoginRequest("jay", "1234");

        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testLoginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andDo(
                        mvcResult -> {
                            log.info(
                                    "What is its status ? : "
                                            + mvcResult.getResponse().getStatus());
                            var signupResponse =
                                    this.objectMapper.readValue(
                                            mvcResult
                                                    .getResponse()
                                                    .getContentAsString(StandardCharsets.UTF_8),
                                            TokenDto.class);

                            log.info("Parsed : " + objectMapper.writeValueAsString(signupResponse));
                            assertThat(signupResponse).isNotNull();
                        })
                .andDo(
                        // Documentation
                        document(
                                "in-house-login",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("userId")
                                                .description("userId"),
                                        PayloadDocumentation.fieldWithPath("password")
                                                .description("password")),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("token")
                                                .type(JsonFieldType.STRING)
                                                .description("JWT 토큰 값"))));
    }

    @Test
    public void inHouseCredentialError() throws Exception {
        var testUserEntity =
                UserEntity.builder()
                        .userInfoId(1L)
                        .userGradeId(1L)
                        .userAccountId("jay")
                        .name("hwang")
                        .email("dlswp113@gmail.com")
                        .phone("010-0000-0000")
                        .address("사랑시고백구행복동")
                        .password(delegatingPasswordEncoder.encode("1234"))
                        .userStatus(UserStatusType.VERIFIED) // Verified된 유저만 로그인 처리.
                        .build();

        userRepository.saveAndFlush(testUserEntity);

        var testLoginRequest = new LoginRequest("jay", "123"); // 패스 워드가 틀림.

        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testLoginRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.errorMessage").exists())
                .andDo(
                        mvcResult -> {
                            log.info(
                                    "What is its status ? : "
                                            + mvcResult.getResponse().getStatus());
                            var errorResponse =
                                    this.objectMapper.readValue(
                                            mvcResult
                                                    .getResponse()
                                                    .getContentAsString(StandardCharsets.UTF_8),
                                            CommonErrorResponse.class);

                            log.info("Parsed : " + objectMapper.writeValueAsString(errorResponse));
                            assertThat(errorResponse).isNotNull();
                        })
                .andDo(
                        // Documentation
                        document(
                                "in-house-login-password-error",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("userId")
                                                .description("userId"),
                                        PayloadDocumentation.fieldWithPath("password")
                                                .description("password")),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("errorCode")
                                                .type(JsonFieldType.NUMBER)
                                                .description("에러 코드"),
                                        PayloadDocumentation.fieldWithPath("errorMessage")
                                                .type(JsonFieldType.STRING)
                                                .description("에러 메시지"))));
    }

    @Test
    public void inHouseNotExistsUserError() throws Exception {
        var testUserEntity =
                UserEntity.builder()
                        .userInfoId(1L)
                        .userGradeId(1L)
                        .userAccountId("jay")
                        .name("hwang")
                        .email("dlswp113@gmail.com")
                        .phone("010-0000-0000")
                        .address("사랑시고백구행복동")
                        .password(delegatingPasswordEncoder.encode("1234"))
                        .userStatus(UserStatusType.VERIFIED) // Verified된 유저만 로그인 처리.
                        .build();

        userRepository.saveAndFlush(testUserEntity);

        var testLoginRequest = new LoginRequest("jay123", "1234"); // 존재하지 않는 유저정보

        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testLoginRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.errorMessage").exists())
                .andDo(
                        mvcResult -> {
                            log.info(
                                    "What is its status ? : "
                                            + mvcResult.getResponse().getStatus());
                            var errorResponse =
                                    this.objectMapper.readValue(
                                            mvcResult
                                                    .getResponse()
                                                    .getContentAsString(StandardCharsets.UTF_8),
                                            CommonErrorResponse.class);

                            log.info("Parsed : " + objectMapper.writeValueAsString(errorResponse));
                            assertThat(errorResponse).isNotNull();
                        })
                .andDo(
                        // Documentation
                        document(
                                "in-house-login-userId-error",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("userId")
                                                .description("userId"),
                                        PayloadDocumentation.fieldWithPath("password")
                                                .description("password")),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("errorCode")
                                                .type(JsonFieldType.NUMBER)
                                                .description("에러 코드"),
                                        PayloadDocumentation.fieldWithPath("errorMessage")
                                                .type(JsonFieldType.STRING)
                                                .description("에러 메시지"))));
    }
}
