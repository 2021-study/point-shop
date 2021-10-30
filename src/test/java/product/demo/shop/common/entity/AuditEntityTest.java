//package product.demo.shop.common.entity;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.ComponentScan.Filter;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import product.demo.shop.configuration.DataBaseConfiguration;
//import product.demo.shop.configuration.ObjectMapperConfig;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest(
//        // DataJpaTest는 Slicing Test이므로, @EnableJpaAuditing으로 자동 등록되는 빈을 가져오기 위해 해당 옵션이 필요함.
//        // https://github.com/spring-projects/spring-boot/issues/13337#issuecomment-394132296
//        includeFilters = @Filter(
//        type = ASSIGNABLE_TYPE,
//        classes = {AccountAuditAware.class, DataBaseConfiguration.class, ObjectMapperConfig.class}
//))
//@ActiveProfiles("test")
//@Slf4j
//public class AuditEntityTest {
//
//    @Autowired
//    private TestEntityRepository repository;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Autowired
//    @Qualifier("commonObjectMapper")
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void auditFieldSetUpTest() throws Exception{
//        var testEntity = new TestEntity();
//        testEntity.setTestField("value1");
//        var savedEntity = this.repository.saveAndFlush(testEntity);
//
//        log.info(this.objectMapper.writeValueAsString(savedEntity));
//
//        assertAll(
//                ()->assertThat(savedEntity.getCreatedAt()).isNotNull(),
//                ()->assertThat(savedEntity.getCreatedBy()).isNotNull(),
//                ()->assertThat(savedEntity.getLastModifiedAt()).isNotNull(),
//                ()->assertThat(savedEntity.getLastModifiedBy()).isNotNull()
//        );
//    }
//
//    @Test
//    public void lastModifiedFieldUpdatedTest() throws Exception{
//        var testEntity = new TestEntity();
//        testEntity.setTestField("value1");
//        var firstSavedEntity= this.repository.saveAndFlush(testEntity);
//
//        em.clear(); // 영속성 컨텍스트 초기화
//        Thread.sleep(500); // last_modified_at을 다른 값으로 셋업하기 위해 500ms 대기
//
//        testEntity.setTestField("value2");
//        var secondSavedEntity= this.repository.saveAndFlush(testEntity);
//
//        assertAll(
//                ()->assertThat(firstSavedEntity.getCreatedAt())
//                        .isEqualTo(secondSavedEntity.getCreatedAt()),
//                ()->assertThat(firstSavedEntity.getLastModifiedAt())
//                        .isNotEqualTo(secondSavedEntity.getLastModifiedAt())
//        );
//    }
//
//}
