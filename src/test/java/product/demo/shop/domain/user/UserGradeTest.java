package product.demo.shop.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import product.demo.shop.common.entity.AccountAuditAware;
import product.demo.shop.configuration.DataBaseConfiguration;
import product.demo.shop.configuration.QuerydslConfig;
import product.demo.shop.domain.grade.entity.UserGradeEntity;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.grade.repository.UserGradeRepository;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.context.annotation.ComponentScan.Filter;
import product.demo.shop.domain.user.repository.custom.UserRepositoryImpl;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

/*Test에 이리 많은 어노테이션이 덕지덕지 붙는게 마음에 들지 않으시면
 *  @SpringBootTest 하나만 붙여주셔도 됩니다.
 * */
@ExtendWith(SpringExtension.class)
@DataJpaTest(
        includeFilters =
                @Filter(
                        type = ASSIGNABLE_TYPE,
                        classes = {
                            AccountAuditAware.class,
                            DataBaseConfiguration.class,
                            QuerydslConfig.class
                        }))
@ActiveProfiles("test")
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserGradeTest {

    @Autowired UserRepository userRepository;

    @Autowired UserGradeRepository userGradeRepository;

    @BeforeAll
    public void setUpTestData() {
        var testUser = UserEntity.sampleUser();
        var testUserGradeList =
                List.of(
                        new UserGradeEntity(GradeName.BRONZE),
                        new UserGradeEntity(GradeName.SILVER),
                        new UserGradeEntity(GradeName.GOLD));

        userRepository.save(testUser);
        userGradeRepository.saveAll(testUserGradeList);
    }

    @Test
    @DisplayName("유저 ID가 입력되었을 때, 유저 등급도 같이 조회화는 조인 쿼리 테스트")
    public void userGradeJoinTest() {
        var registeredUser =
                userRepository
                        .findAll()
                        .stream()
                        .findFirst()
                        .orElseThrow(
                                () -> {
                                    throw new NoSuchElementException("주어진 조건의 User가 없습니다.");
                                });

        var searchedUserInfoDto = userRepository.findUserWithUserGradeInfo(registeredUser.getUserInfoId());

        assertAll(
                ()->assertNotNull(searchedUserInfoDto),
                ()->assertEquals(registeredUser.getEmail(),searchedUserInfoDto.getEmail()),
                ()->assertEquals(registeredUser.getUserGradeId(), searchedUserInfoDto.getUserGradeId())
        );
    }

    @Test
    @DisplayName("유저 등록 시 암호화 되는 부분은 DB에 Save할 때 자동으로 암호화 되고, 읽을 때 자동으로 복호화 되어야 한다.")
    public void saveUserEncryptDecryptTest(){
        var registeredUser =
                userRepository
                        .findAll()
                        .stream()
                        .findFirst()
                        .orElseThrow(
                                () -> {
                                    throw new NoSuchElementException("주어진 조건의 User가 없습니다.");
                                });

        assertThat(registeredUser.getName()).isEqualTo("sample");
        var newUser = UserEntity.sampleUser();
        newUser.setName("Jacob");
        var newRegisterUser  = userRepository.saveAndFlush(newUser);
        assertThat(newRegisterUser.getName()).isEqualTo("Jacob");
    }
}
