package product.demo.shop.domain.verification.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import product.demo.shop.domain.user.service.CustomUserDetailsService;
import product.demo.shop.domain.verification.service.EmailVerificationService;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@WebMvcTest(VerificationRestController.class)
@AutoConfigureRestDocs
class VerificationRestControllerTest {

    final String VERIFICATION_CODE = "0afdf841-e2f7-4a70-8d75-1865332ec375";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private EmailVerificationService emailVerificationService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void 이메일_인증_컨트롤러_테스트() throws Exception {
        mockMvc.perform(
            get("/verify/email/{verificationCode}",VERIFICATION_CODE)
        ).andExpect(
            MockMvcResultMatchers.status().is2xxSuccessful()
        ).andDo(
            document("verification/email",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("verificationCode").description("인증 코드")
                )
            )
        );
    }
}