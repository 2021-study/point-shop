package product.demo.shop.exception;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import product.demo.shop.common.exception.CommonErrorResponse;
import product.demo.shop.common.logger.CommonLogger;
import product.demo.shop.configuration.EnableMockMvc;

@SpringBootTest
@EnableMockMvc
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommonErrorCodeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Qualifier("commonObjectMapper")
    private ObjectMapper objectMapper;

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
                    CommonLogger.info("What is istatus : "
                            + mvcResult.getResponse().getStatus(), CommonErrorCodeTest.class);

                    CommonErrorResponse errorResponse = this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(
                            StandardCharsets.UTF_8), CommonErrorResponse.class);

                    assertThat("알수 없는 서버 에러 입니다.").isEqualTo(errorResponse.getErrorMessage());
                });
    }
}
