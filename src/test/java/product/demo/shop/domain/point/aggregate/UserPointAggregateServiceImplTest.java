package product.demo.shop.domain.point.aggregate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.domain.point.entity.PointEventEntity;
import product.demo.shop.domain.point.entity.enums.PointEventType;
import product.demo.shop.domain.point.repository.PointEventEntityRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserPointAggregateServiceImplTest {

    @Mock private PointEventEntityRepository pointEventEntityRepository;

    @InjectMocks private UserPointAggregateServiceImpl userPointAggregateService;

    @Test
    @DisplayName("userInfoId를 입력받아 Page<Entity> -> Page<Dto>로 변환이 되어야 한다.")
    public void searchUserPointDetailsTest() {
        var testList =
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

        var testPageImpl = new PageImpl<>(testList);

        when(pointEventEntityRepository.findByUserInfoId(1L, PageRequest.of(0, 5)))
                .thenReturn(testPageImpl);

        var result = userPointAggregateService.searchUserPointDetails(1L, PageRequest.of(0, 5));
        log.info("result size : " + result.getTotalElements());
        assertAll(
                () -> assertEquals(3, result.getTotalElements()),
                () ->
                        assertTrue(
                                result.getContent()
                                        .contains(testList.get(0).toUserPointResponseDetail())));
    }
}
