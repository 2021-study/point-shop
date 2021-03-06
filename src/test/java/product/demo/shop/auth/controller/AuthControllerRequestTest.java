package product.demo.shop.auth.controller;

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
import product.demo.shop.auth.dto.requests.SignupRequest;

import java.util.Locale;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static product.demo.shop.auth.controller.AuthController.AUTH_API_PATH;

/* ??? ????????? ????????? */
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
                                webApplicationContext) // mock ????????? ?????? WebApplicationContext??? ?????? ?????????.
                        .addFilter(new CharacterEncodingFilter("UTF-8", true)) // REST Doc ?????? ?????? ??????.
                        .apply(documentationConfiguration(restDocumentationContextProvider))
                        .build();
    }

    @Test
    @DisplayName("?????? ?????? ??? Email ????????? ?????? ?????? ????????? spring-validation??? ?????? ?????? ????????? ??????.")
    public void validationEmailCheckFailTest() throws Exception {
        var testSignupReqest =
                SignupRequest.builder()
                        .email("newUser-gmail.com") // email ????????? ?????? ??????.
                        .userAccountId("nickname")
                        .name("real-name")
                        .password("124334")
                        .phoneNumber("010-3333-2222")
                        .address("???????????????????????????")
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
                                                "[([Field] : email  [Value] : newUser-gmail.com  [Message] : ????????? ????????? ????????? ???????????? ?????????)]")))
                .andDo(
                        // Documentation
                        document(
                                "email-validation-failed-in-signing-up",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("email")
                                                .description("email"),
                                        PayloadDocumentation.fieldWithPath("userAccountId")
                                                .description("userAccountId"),
                                        PayloadDocumentation.fieldWithPath("name")
                                                .description("name"),
                                        PayloadDocumentation.fieldWithPath("password")
                                                .description("password"),
                                        PayloadDocumentation.fieldWithPath("phoneNumber")
                                                .description("phoneNumber"),
                                        PayloadDocumentation.fieldWithPath("address")
                                                .description("address")),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("errorCode")
                                                .type(JsonFieldType.NUMBER)
                                                .description("?????? ??????"),
                                        PayloadDocumentation.fieldWithPath("errorMessage")
                                                .type(JsonFieldType.STRING)
                                                .description("?????? ?????????"))));
    }

    @Test
    @DisplayName("?????? ?????? ??? Not Null ????????? ?????? ?????? ????????? spring-validation??? ?????? ?????? ????????? ??????.")
    public void validationNotNullFieldFailTest() throws Exception {
        var testSignupReqest =
                SignupRequest.builder()
                        .email("newUser@gmail.com")
                        .userAccountId(null) // Not Null ????????? ?????? ??????.
                        .name("real-name")
                        .password("124334")
                        .phoneNumber("010-3333-2222")
                        .address("???????????????????????????")
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
                                                "[([Field] : userAccountId  [Value] : null  [Message] : ??????????????? ????????????)]")))
                .andDo(
                        // Documentation
                        document(
                                "notnull-validation-failed-in-signing-up",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("email")
                                                .description("email"),
                                        //
                                        // PayloadDocumentation.fieldWithPath("userAccountId").description("userAccountId"),
                                        PayloadDocumentation.fieldWithPath("name")
                                                .description("name"),
                                        PayloadDocumentation.fieldWithPath("password")
                                                .description("password"),
                                        PayloadDocumentation.fieldWithPath("phoneNumber")
                                                .description("phoneNumber"),
                                        PayloadDocumentation.fieldWithPath("address")
                                                .description("address")),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("errorCode")
                                                .type(JsonFieldType.NUMBER)
                                                .description("?????? ??????"),
                                        PayloadDocumentation.fieldWithPath("errorMessage")
                                                .type(JsonFieldType.STRING)
                                                .description("?????? ?????????"))));
    }
}
