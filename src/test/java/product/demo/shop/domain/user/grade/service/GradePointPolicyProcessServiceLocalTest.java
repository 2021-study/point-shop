package product.demo.shop.domain.user.grade.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyInputDto;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.service.GradePointPolicyProcessService;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("local")
@Disabled
@Slf4j
public class GradePointPolicyProcessServiceLocalTest {

    @Autowired
    GradePointPolicyProcessService pointPolicyProcessService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("정액 포인트 추가 적립 테스트")
    public void sampleTest() throws JsonProcessingException {
        var testGradePolicyInputDto = GradePolicyInputDto.of(
                GradeName.BRONZE,
                GradePolicyObject.POINT,
                BigDecimal.valueOf(1_000L),
                null,
                null
        );
        var resval = pointPolicyProcessService.appliedGradePolicy(testGradePolicyInputDto);

        log.info(this.objectMapper.writeValueAsString(resval));
    }
}
