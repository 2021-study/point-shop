package product.demo.shop.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.user.dto.UserInfoDto;
import product.demo.shop.domain.user.repository.UserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("local")
@Slf4j
@Disabled
public class UserGradeJoinTest {

    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("Querydsl Join Test - 유저 등급과 유저 정보 조회")
    public void userInfoDtoTest() {
        UserInfoDto userInfoDto = this.userRepository.findMyUserComprehensiveInfo(1L);

        log.info("USER GRADE NAME : " + userInfoDto.getUserGradeName());
        log.info("USER GRADE Email : " + userInfoDto.getEmail());

        assertThat(userInfoDto.getUserGradeName()).isEqualTo(GradeName.BRONZE);
        assertThat(userInfoDto.getEmail()).isEqualTo("sample@email.com");
    }
}
