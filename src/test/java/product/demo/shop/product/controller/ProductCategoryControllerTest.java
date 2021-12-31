package product.demo.shop.product.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static product.demo.shop.domain.product.controller.ProductCategoryController.PRODUCT_CATEGORY_PATH;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Locale;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import product.demo.shop.domain.grade.testcreator.ProductCategoryCreator;
import product.demo.shop.domain.product.dto.query.ProductCategoryQueryDto;
import product.demo.shop.domain.product.repository.ProductCategoryRepository;

@Slf4j
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductCategoryControllerTest {

    private MockMvc mockMvc;

    @Autowired private ProductCategoryRepository productCategoryRepository;

    @Autowired private ObjectMapper objectMapper;

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

        var creator = new ProductCategoryCreator(this.productCategoryRepository);
        creator.setUp();
    }

    @Test
    @WithMockUser
    @DisplayName("단일 카테고리 조회")
    public void findSingleCategory() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(
                                        PRODUCT_CATEGORY_PATH + "?categoryId=1&categoryName=null")
                                .locale(Locale.KOREA)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        // Documentation
                        document(
                                "single-product-category",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.subsectionWithPath("productCategoryId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("카테고리 ID"),
                                        PayloadDocumentation.subsectionWithPath("parent")
                                                .type(JsonFieldType.NUMBER)
                                                .description("부모 카테고리ID"),
                                        PayloadDocumentation.subsectionWithPath(
                                                        "productCategoryName")
                                                .type(JsonFieldType.STRING)
                                                .description("카테고리 명"))));
    }

    @Test
    @DisplayName("카테고리 리스트 조회")
    public void findMultipleCategory() throws Exception {
        var request =
                new ProductCategoryQueryDto(1L, null, null, null, null, null, null, null, null);

        mockMvc.perform(
                        RestDocumentationRequestBuilders.post(
                                        PRODUCT_CATEGORY_PATH + "/list?page=0&size=5")
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.ALL_VALUE)
                                .locale(Locale.KOREA)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        // Documentation
                        document(
                                "multi-product-categories",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.subsectionWithPath("pageable")
                                                .type(JsonFieldType.STRING)
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
                                        PayloadDocumentation.fieldWithPath(
                                                        "content[].bigProductCategoryId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("대분류 카테고리 ID"),
                                        PayloadDocumentation.fieldWithPath("content[].bigParent")
                                                .type(JsonFieldType.NUMBER)
                                                .description("대분류 parent ID"),
                                        PayloadDocumentation.fieldWithPath(
                                                        "content[].bigCategoryName")
                                                .type(JsonFieldType.STRING)
                                                .description("대분류 카테고리명"),
                                        PayloadDocumentation.fieldWithPath(
                                                        "content[].middleProductCategoryId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("중분류 카테고리 ID"),
                                        PayloadDocumentation.fieldWithPath("content[].middleParent")
                                                .type(JsonFieldType.NUMBER)
                                                .description("중분류 parent ID"),
                                        PayloadDocumentation.fieldWithPath(
                                                        "content[].middleProductCategoryName")
                                                .type(JsonFieldType.STRING)
                                                .description("중분류 카테고리명"),
                                        PayloadDocumentation.fieldWithPath(
                                                        "content[].smallProductCategoryId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("소분류 카테고리 ID"),
                                        PayloadDocumentation.fieldWithPath("content[].smallParent")
                                                .type(JsonFieldType.NUMBER)
                                                .description("소분류 parent ID"),
                                        PayloadDocumentation.fieldWithPath(
                                                        "content[].smallProductCategoryName")
                                                .type(JsonFieldType.STRING)
                                                .description("소분류 카테고리명"))));
    }
}
