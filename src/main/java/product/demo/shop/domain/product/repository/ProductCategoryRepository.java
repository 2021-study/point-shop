package product.demo.shop.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import product.demo.shop.domain.product.dto.query.MultipleCategoryQueryDtoInterface;
import product.demo.shop.domain.product.dto.query.ProductCategoryQueryDto;
import product.demo.shop.domain.product.entity.ProductCategoryEntity;
import product.demo.shop.domain.product.repository.custom.CustomProductCategoryRepository;

public interface ProductCategoryRepository
        extends JpaRepository<ProductCategoryEntity, Long>, CustomProductCategoryRepository {

    // 그냥 대분류는 대분류 대로, 중분류는 중분류대로, 소분류는 소분류 대로 조회를 하는게...
    // TODO : Query 정합성 문제
    @Query(value = """
    SELECT
         t1.parent as bigProductCategoryId,
         (SELECT
            product_category_name
     	 FROM tb_product_category
          WHERE product_category_id = t1.parent
         ) as bigCategoryName,
         t2.parent as middleProductCategoryId,
     	 t1.product_category_name as middleCategoryName,
         t2.product_category_id as smallProductCategoryId,
         t2.product_category_name as smallProductCategoryName
     FROM
         tb_product_category as t1
             INNER JOIN
         tb_product_category as t2 ON (t1.product_category_id = t2.parent)
        WHERE
            t2.product_category_id = COALESCE(?1, t2.product_category_id)
        OR
            t2.product_category_name = COALESCE(?2,t2.product_category_name)
        """,
            countQuery = """
                SELECT
                    count(*)
                FROM
                    tb_product_category
                WHERE
                    product_category_id = COALESCE(?1, product_category_id)
                 OR
                    product_category_name = COALESCE(?2, product_category_name)
            """,
            nativeQuery = true)
    Page<MultipleCategoryQueryDtoInterface> findAllProductCategories(
            @Param("productCategoryId") Long productCategoryId,
            @Param("productCategoryId") String productCategoryName,
            Pageable pageable);
}
