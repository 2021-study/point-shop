package product.demo.shop.common.exception;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

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
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

/** 이건 Spring restdoc과 CommonExceptionHandler 테스트용 코드입니다.
 *  실제 문서화가 필요한 API를 위해 참고용으로 만들어 놓은 코드로 보시면 됩니다.
 */
@Slf4j
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest
//@EnableMockMvc // REST-Doc을 사용하는 경우 MockMvc가 나중에 초기화되므로 해당 어노테이션이 먹히지 않음.
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommonErrorCodeTest {


    private MockMvc mockMvc;

    @Autowired
    @Qualifier("commonObjectMapper")
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider)	{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true)) // REST Doc 한글 깨짐 방지.
                .apply(documentationConfiguration(restDocumentationContextProvider))
//                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @WithMockUser
    public void unknownErrorCodeTest() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/exception-test/unknown-error")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andDo(mvcResult->{
                    log.info("What is istatus : "
                            + mvcResult.getResponse().getStatus());

                    CommonErrorResponse errorResponse = this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(
                            StandardCharsets.UTF_8), CommonErrorResponse.class);

                    assertThat("알수 없는 서버 에러 입니다.").isEqualTo(errorResponse.getErrorMessage());
                })
                .andDo(
                        document("unknown-error-code-test",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("errorCode")
                                                .description("Error Code 필드 입니다."),
                                        PayloadDocumentation.fieldWithPath("errorMessage")
                                                .description("에러 메시지 필드 입니다.")
                                )));
    }

    @Test
    @WithMockUser
    public void commonExceptionWithDescription() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/exception-test/unknown-error-with-description")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andDo(mvcResult->{
                    log.info("What is istatus : "
                            + mvcResult.getResponse().getStatus());

                    CommonErrorResponse errorResponse = this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(
                            StandardCharsets.UTF_8), CommonErrorResponse.class);

                    assertThat("알수 없는 서버 에러 입니다.").isEqualTo(errorResponse.getErrorMessage());
                })
                .andDo(
                        document("common-exception-with-description",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("errorCode")
                                                .description("Error Code 필드 입니다."),
                                        PayloadDocumentation.fieldWithPath("errorMessage")
                                                .description("에러 메시지 필드 입니다.")
                                )));
    }

    @Test
    @WithMockUser
    public void commonExceptionWithDescriptionAndThrowable() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/exception-test/unknown-error-with-description-throwable")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andDo(mvcResult->{
                    log.info("What is istatus : "
                            + mvcResult.getResponse().getStatus());

                    CommonErrorResponse errorResponse = this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(
                            StandardCharsets.UTF_8), CommonErrorResponse.class);

                    assertThat("알수 없는 서버 에러 입니다.").isEqualTo(errorResponse.getErrorMessage());
                })
                .andDo(
                        document("common-exception-with-description-and-throwable",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("errorCode")
                                                .description("Error Code 필드 입니다."),
                                        PayloadDocumentation.fieldWithPath("errorMessage")
                                                .description("에러 메시지 필드 입니다.")
                                )));
    }
}
