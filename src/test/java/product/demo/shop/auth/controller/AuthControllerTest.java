package product.demo.shop.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import org.springframework.web.filter.CharacterEncodingFilter;
import product.demo.shop.auth.dto.MailValidationDto;
import product.demo.shop.auth.dto.requests.MailValidationRequest;
import product.demo.shop.auth.dto.requests.SignupRequest;
import product.demo.shop.auth.dto.responses.SignUpCompleteResponse;
import product.demo.shop.auth.dto.responses.SignupResponse;
import product.demo.shop.auth.service.AuthServiceImpl;
import product.demo.shop.domain.user.entity.enums.UserStatusType;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static product.demo.shop.auth.controller.AuthController.AUTH_API_PATH;

@Slf4j
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock private AuthServiceImpl authService;

    private AuthController authController;

    @Autowired
    @Qualifier("commonObjectMapper")
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentationContextProvider) {

        this.authController = new AuthController(this.authService);
        this.mockMvc =
                MockMvcBuilders.standaloneSetup(authController)
                        .addFilter(new CharacterEncodingFilter("UTF-8", true)) // REST Doc 한글 깨짐 방지.
                        .apply(documentationConfiguration(restDocumentationContextProvider))
                        .build();
    }

    @Test
    public void registerNewUserInHouseSignUp() throws Exception {
        //        when(userRepository.existsByUserAccountId(any())).thenReturn(true);
        when(authService.newUserSignUp(any()))
                .thenReturn(
                        MailValidationDto.makeValidationCodeFromMailValidRequest(
                                MailValidationRequest.of("newUser@google.com", 1L)));

        var testSignupReqest =
                SignupRequest.builder()
                        .email("newUser@google.com")
                        .userAccountId("nickname")
                        .name("real-name")
                        .password("124334")
                        .phoneNumber("010-3333-2222")
                        .address("사랑시고백구행복동")
                        .build();

        mockMvc.perform(
                        RestDocumentationRequestBuilders.post(AUTH_API_PATH + "/sign-up")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testSignupReqest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
                                            SignupResponse.class);

                            log.info("Parsed : " + objectMapper.writeValueAsString(signupResponse));
                            assertThat(signupResponse).isNotNull();
                        })
                .andDo(
                        // Documentation
                        document(
                                "register-new-user-in-house-sign-up",
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
                                        PayloadDocumentation.fieldWithPath("email")
                                                .type(JsonFieldType.STRING)
                                                .description("가입 이메일 주소"),
                                        PayloadDocumentation.fieldWithPath("userInfoId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("UserInfoId"),
                                        PayloadDocumentation.fieldWithPath("status")
                                                .type(JsonFieldType.STRING)
                                                .description("상태값"))));
    }

    @Test
    public void validationNewUserTest() throws Exception {
        when(authService.completeSignUp(any(), any()))
                .thenReturn(SignUpCompleteResponse.of("jay", UserStatusType.VERIFIED));

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(
                                AUTH_API_PATH + "/verify/{userInfoId}/{tokenValue}",
                                1L,
                                "token-string"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        mvcResult -> {
                            log.info(
                                    "What is its status ? : "
                                            + mvcResult.getResponse().getStatus());
                            var signUpCompleteResponse =
                                    this.objectMapper.readValue(
                                            mvcResult
                                                    .getResponse()
                                                    .getContentAsString(StandardCharsets.UTF_8),
                                            SignUpCompleteResponse.class);

                            log.info(
                                    "Parsed : "
                                            + objectMapper.writeValueAsString(
                                                    signUpCompleteResponse));
                            assertThat(signUpCompleteResponse).isNotNull();
                        })
                .andDo(
                        // Documentation
                        document(
                                "validation-new-user-test",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("userInfoId").description("userInfoId"),
                                        parameterWithName("tokenValue").description("tokenValue")),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("userAccountId")
                                                .type(JsonFieldType.STRING)
                                                .description("유저 가입 계졍(ID)"),
                                        PayloadDocumentation.fieldWithPath("userStatus")
                                                .type(JsonFieldType.STRING)
                                                .description("유저 상태"))));
    }
}
