package product.demo.shop.domain.point.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import product.demo.shop.domain.point.entity.PointEventEntity;
import product.demo.shop.domain.point.entity.enums.PointEventType;
import product.demo.shop.domain.point.repository.PointEventEntityRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static product.demo.shop.domain.point.controller.UserPointController.USER_POINT_PATH;

@Slf4j
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PointControllerTest {

    private MockMvc mockMvc;

    @Autowired
    @Qualifier("commonObjectMapper")
    private ObjectMapper objectMapper;

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

        var samplePointEventList =
                List.of(
                        PointEventEntity.of(
                                1L,
                                PointEventType.SAVE,
                                10,
                                "CODE",
                                "CODE_ID",
                                "DETAIL",
                                LocalDateTime.now().plusYears(1L)),
                        PointEventEntity.of(
                                1L,
                                PointEventType.USE,
                                -5,
                                "CODE",
                                "CODE_ID",
                                "DETAIL",
                                LocalDateTime.now().plusYears(1L)),
                        PointEventEntity.of(
                                1L,
                                PointEventType.SAVE,
                                10,
                                "CODE",
                                "CODE_ID",
                                "DETAIL",
                                LocalDateTime.now().plusYears(1L)));

        this.pointEventEntityRepository.saveAll(samplePointEventList);
    }

    @Test
    @DisplayName("userInfoId를 받아서 해당 user의 포인트 가용 정보를 내려준다.")
    public void searchUserPointDetailTest() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(USER_POINT_PATH + "/1?page=0&size=5")
                                .accept(MediaType.ALL_VALUE)
                                .locale(Locale.KOREA)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        // Documentation
                        document(
                                "search-user-point-details",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.subsectionWithPath("pageable")
                                                .type(JsonFieldType.OBJECT)
                                                .description("pageable"),
                                        PayloadDocumentation.subsectionWithPath("sort")
                                                .type(JsonFieldType.OBJECT)
                                                .description("sort"),
                                        PayloadDocumentation.fieldWithPath("last")
                                                .type(JsonFieldType.BOOLEAN)
                                                .description("last"),
                                        PayloadDocumentation.fieldWithPath("totalPages")
                                                .type(JsonFieldType.NUMBER)
                                                .description("totalPages"),
                                        PayloadDocumentation.fieldWithPath("totalElements")
                                                .type(JsonFieldType.NUMBER)
                                                .description("totalElements"),
                                        PayloadDocumentation.fieldWithPath("size")
                                                .type(JsonFieldType.NUMBER)
                                                .description("size"),
                                        PayloadDocumentation.fieldWithPath("number")
                                                .type(JsonFieldType.NUMBER)
                                                .description("number"),
                                        PayloadDocumentation.fieldWithPath("numberOfElements")
                                                .type(JsonFieldType.NUMBER)
                                                .description("numberOfElements"),
                                        PayloadDocumentation.fieldWithPath("first")
                                                .type(JsonFieldType.BOOLEAN)
                                                .description("first"),
                                        PayloadDocumentation.fieldWithPath("empty")
                                                .type(JsonFieldType.BOOLEAN)
                                                .description("empty"),
                                        PayloadDocumentation.fieldWithPath("content[].pointEventId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("포인트 eventId"),
                                        PayloadDocumentation.fieldWithPath("content[].pointEventId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("포인트 eventId"),
                                        PayloadDocumentation.fieldWithPath("content[].eventType")
                                                .type(JsonFieldType.STRING)
                                                .description("포인트 eventType"),
                                        PayloadDocumentation.fieldWithPath("content[].point")
                                                .type(JsonFieldType.NUMBER)
                                                .description("포인트 액수"),
                                        PayloadDocumentation.fieldWithPath(
                                                        "content[].pointExpiredDate")
                                                .type(JsonFieldType.STRING)
                                                .description("포인트 만료 일자"))));
    }
}
