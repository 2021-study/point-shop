package product.demo.shop.domain.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import product.demo.shop.domain.grade.repository.UserGradeRepository;
import product.demo.shop.domain.grade.testcreator.UserPointTestDataCreator;
import product.demo.shop.domain.point.repository.PointEventEntityRepository;
import product.demo.shop.domain.user.repository.UserRepository;

import java.util.Locale;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static product.demo.shop.domain.user.controller.UserInfoController.USER_INFO_API_PATH;

@Slf4j
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired private UserRepository userRepository;

    @Autowired private UserGradeRepository userGradeRepository;

    @Autowired private PointEventEntityRepository pointEventEntityRepository;

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

        var creator =
                new UserPointTestDataCreator(
                        userRepository, userGradeRepository, pointEventEntityRepository);
        creator.setUpSampleData();
    }

    @Test
    @DisplayName("User에 대한 기본 정보 조회 API")
    public void findMyUserComprehensiveInfoTest() throws Exception {

        var testUser = userRepository.findByUserAccountId("sampleUser");
        log.info("testUser : " + testUser.get());
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(
                                        USER_INFO_API_PATH + "/" + testUser.get().getUserInfoId())
                                .accept(MediaType.ALL_VALUE)
                                .locale(Locale.KOREA)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.totalUsablePoint").value(1750))
                .andDo(
                        // Documentation
                        document(
                                "find-comprehensive-user-info",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.subsectionWithPath("userId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("userInfoId"),
                                        PayloadDocumentation.subsectionWithPath("userAccountId")
                                                .type(JsonFieldType.STRING)
                                                .description("userAccountId"),
                                        PayloadDocumentation.fieldWithPath("name")
                                                .type(JsonFieldType.STRING)
                                                .description("name"),
                                        PayloadDocumentation.fieldWithPath("email")
                                                .type(JsonFieldType.STRING)
                                                .description("email"),
                                        PayloadDocumentation.fieldWithPath("phone")
                                                .type(JsonFieldType.STRING)
                                                .description("phone"),
                                        PayloadDocumentation.fieldWithPath("address")
                                                .type(JsonFieldType.STRING)
                                                .description("address"),
                                        PayloadDocumentation.fieldWithPath("userGradeId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("userGradeId"),
                                        PayloadDocumentation.fieldWithPath("userGradeName")
                                                .type(JsonFieldType.STRING)
                                                .description("userGradeName"),
                                        PayloadDocumentation.fieldWithPath("totalUsablePoint")
                                                .type(JsonFieldType.NUMBER)
                                                .description("totalUsablePoint"))));
    }
}
