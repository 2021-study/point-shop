package product.demo.shop.domain.user.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import product.demo.shop.domain.auth.dto.requests.SignupRequest;

import java.util.Locale;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static product.demo.shop.domain.auth.controller.AuthController.AUTH_API_PATH;

/* 실 구현체 테스트 */
@Slf4j
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerRequestTest {

    private MockMvc mockMvc;

    @Autowired
    @Qualifier("commonObjectMapper")
    private ObjectMapper objectMapper;

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
    @DisplayName("회원 가입 시 Email 형식에 맞지 않는 요청은 spring-validation에 의해 체크 되어야 한다.")
    public void validationEmailCheckFailTest() throws Exception {
        var testSignupReqest =
                SignupRequest.builder()
                        .email("newUser-gmail.com") // email 형식에 맞지 않음.
                        .userAccountId("nickname")
                        .name("real-name")
                        .password("124334")
                        .phoneNumber("010-3333-2222")
                        .address("사랑시고백구행복동")
                        .build();

        mockMvc.perform(
                        RestDocumentationRequestBuilders.post(AUTH_API_PATH + "/sign-up")
                                .accept(MediaType.ALL_VALUE)
                                .locale(Locale.KOREA)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testSignupReqest)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(Matchers.is(400)))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.errorMessage")
                                .value(
                                        Matchers.is(
                                                "[([Field] : email  [Value] : newUser-gmail.com  [Message] : 올바른 형식의 이메일 주소여야 합니다)]")))
                .andDo(
                        // Documentation
                        document(
                                "email-validation-failed-in-signing-up",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("email").description("email"),
                                        PayloadDocumentation.fieldWithPath("userAccountId").description("userAccountId"),
                                        PayloadDocumentation.fieldWithPath("name").description("name"),
                                        PayloadDocumentation.fieldWithPath("password").description("password"),
                                        PayloadDocumentation.fieldWithPath("phoneNumber").description("phoneNumber"),
                                        PayloadDocumentation.fieldWithPath("address").description("address")
                                ),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("errorCode")
                                                .type(JsonFieldType.NUMBER)
                                                .description("에러 코드"),
                                        PayloadDocumentation.fieldWithPath("errorMessage")
                                                .type(JsonFieldType.STRING)
                                                .description("에러 메시지"))));
    }

    @Test
    @DisplayName("회원 가입 시 Not Null 항목에 맞지 않는 요청은 spring-validation에 의해 체크 되어야 한다.")
    public void validationNotNullFieldFailTest() throws Exception {
        var testSignupReqest =
                SignupRequest.builder()
                        .email("newUser@gmail.com")
                        .userAccountId(null) // Not Null 형식에 맞지 않음.
                        .name("real-name")
                        .password("124334")
                        .phoneNumber("010-3333-2222")
                        .address("사랑시고백구행복동")
                        .build();

        mockMvc.perform(
                        RestDocumentationRequestBuilders.post(AUTH_API_PATH + "/sign-up")
                                .accept(MediaType.ALL_VALUE)
                                .locale(Locale.KOREA)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testSignupReqest)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(Matchers.is(400)))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.errorMessage")
                                .value(
                                        Matchers.is(
                                                "[([Field] : userAccountId  [Value] : null  [Message] : 널이어서는 안됩니다)]")))
                .andDo(
                        // Documentation
                        document(
                                "notnull-validation-failed-in-signing-up",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("email").description("email"),
//                                        PayloadDocumentation.fieldWithPath("userAccountId").description("userAccountId"),
                                        PayloadDocumentation.fieldWithPath("name").description("name"),
                                        PayloadDocumentation.fieldWithPath("password").description("password"),
                                        PayloadDocumentation.fieldWithPath("phoneNumber").description("phoneNumber"),
                                        PayloadDocumentation.fieldWithPath("address").description("address")
                                ),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("errorCode")
                                                .type(JsonFieldType.NUMBER)
                                                .description("에러 코드"),
                                        PayloadDocumentation.fieldWithPath("errorMessage")
                                                .type(JsonFieldType.STRING)
                                                .description("에러 메시지"))));
    }
}
