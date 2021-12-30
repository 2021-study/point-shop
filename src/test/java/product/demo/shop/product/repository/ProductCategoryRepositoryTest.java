package product.demo.shop.product.repository;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import product.demo.shop.common.entity.AccountAuditAware;
import product.demo.shop.configuration.DataBaseConfiguration;
import product.demo.shop.configuration.ObjectMapperConfig;
import product.demo.shop.configuration.QuerydslConfig;
import product.demo.shop.domain.grade.testcreator.GradePolicyTestDataCreator;
import product.demo.shop.domain.grade.testcreator.ProductCategoryCreator;
import product.demo.shop.domain.product.dto.query.ProductCategoryQueryDto;
import product.demo.shop.domain.product.repository.ProductCategoryRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest(
        includeFilters =
                @ComponentScan.Filter(
                        type = ASSIGNABLE_TYPE,
                        classes = {
                            AccountAuditAware.class,
                            DataBaseConfiguration.class,
                            QuerydslConfig.class,
                            ObjectMapperConfig.class
                        }))
@ActiveProfiles("test")
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductCategoryRepositoryTest {

    @Autowired private ProductCategoryRepository productCategoryRepository;

    @Autowired private ObjectMapper objectMapper;

    @BeforeAll
    public void setUp() {
        var creator = new ProductCategoryCreator(this.productCategoryRepository);
        creator.setUp();
    }

    @Test
    @DisplayName("categoryId로 카테고리 1건이 조회도어야 한다.")
    public void findCategorybyCategoryId() throws JsonProcessingException {
        var result =
                this.productCategoryRepository.findProductCategory(1L, null);

        log.info(objectMapper.writeValueAsString(result));
    }

    @Test
    @DisplayName("categoryName으로 카테고리 1건이 조회되어야 한다.")
    public void findCategoryByCategoryName() throws JsonProcessingException {
        var result =
                this.productCategoryRepository.findProductCategory(null,"농산물");

        log.info(objectMapper.writeValueAsString(result));
    }

    @Test
    @DisplayName("대분류/중분류/소분류 모두가 같이 조회되어야 한다.")
    public void findAllCategories() throws JsonProcessingException {
        var page = PageRequest.of(0, 5);
        var result = this.productCategoryRepository.findAllProductCategories(5L, null, page);

        log.info(">>> result size : " + result.getTotalElements());
        log.info(objectMapper.writeValueAsString(result));
    }
}
