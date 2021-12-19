package product.demo.shop.domain.grade.testcreator;

import product.demo.shop.domain.grade.entity.UserGradeEntity;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.grade.repository.UserGradeRepository;
import product.demo.shop.domain.point.entity.PointEventEntity;
import product.demo.shop.domain.point.entity.enums.PointEventType;
import product.demo.shop.domain.point.repository.PointEventEntityRepository;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.entity.enums.UserStatusType;
import product.demo.shop.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class UserPointTestDataCreator {
    private UserRepository userRepository;

    private UserGradeRepository userGradeRepository;

    private PointEventEntityRepository pointEventEntityRepository;

    public UserPointTestDataCreator(
            UserRepository userRepository,
            UserGradeRepository userGradeRepository,
            PointEventEntityRepository pointEventEntityRepository) {
        this.userRepository = userRepository;
        this.userGradeRepository = userGradeRepository;
        this.pointEventEntityRepository = pointEventEntityRepository;
    }

    private UserEntity makeSampleUser(String name, long gradeId) {
        return UserEntity.builder()
                .userGradeId(gradeId)
                .userAccountId("sampleUser")
                .snsProviderType("sns_provider")
                .name(name)
                .email("sample@email.com")
                .phone("010-1111-1111")
                .password("password")
                .address("address")
                .userStatus(UserStatusType.VERIFIED)
                .build();
    }

    public void setUpSampleData() {
        deleteData();
        setUpTestData();
    }

    private void deleteData() {
        userGradeRepository.deleteAll();
        userRepository.deleteAll();
        pointEventEntityRepository.deleteAll();
    }

    private void setUpTestData() {

        var testUserGradeList =
                List.of(
                        UserGradeEntity.builder().gradeName(GradeName.BRONZE).build(),
                        UserGradeEntity.builder().gradeName(GradeName.SILVER).build(),
                        UserGradeEntity.builder().gradeName(GradeName.GOLD).build());

        var gradeList = userGradeRepository.saveAll(testUserGradeList);
        var testUser = makeSampleUser("sample", gradeList.get(0).getUserGradeId());
        var savedUser = userRepository.save(testUser);

        var testPointEventList =
                List.of(
                        PointEventEntity.of(
                                savedUser.getUserInfoId(),
                                PointEventType.SAVE,
                                1_000,
                                "POINT",
                                "TB_POINT_EVENT",
                                "테스트적립",
                                LocalDateTime.now().plusYears(1L)),
                        PointEventEntity.of(
                                savedUser.getUserInfoId(),
                                PointEventType.SAVE,
                                1_000,
                                "POINT",
                                "TB_POINT_EVENT",
                                "테스트적립",
                                LocalDateTime.now().plusYears(1L)),
                        PointEventEntity.of(
                                savedUser.getUserInfoId() + 1L,
                                PointEventType.SAVE,
                                1_000,
                                "POINT",
                                "TB_POINT_EVENT",
                                "테스트적립",
                                LocalDateTime.now().plusYears(1L)),
                        PointEventEntity.of(
                                savedUser.getUserInfoId(),
                                PointEventType.USE,
                                -250,
                                "POINT",
                                "TB_POINT_EVENT",
                                "테스트적립",
                                LocalDateTime.now().plusYears(1L)));

        pointEventEntityRepository.saveAll(testPointEventList);
    }
}
