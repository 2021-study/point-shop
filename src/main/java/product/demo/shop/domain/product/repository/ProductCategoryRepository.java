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
            bmt.b_product_category_id as bigProductCategoryId,
            bmt.b_parent as bigParent,
            bmt.b_product_category_name as bigCategoryName,
            bmt.m_product_category_id as middleProductCategoryId,
            bmt.m_parent as middleParent,
            bmt.m_product_category_name as middleProductCategoryName,
            st.product_category_id as smallProductCategoryId,
            st.parent as smallParent,
            st.product_category_name as smallProductCategoryName
        FROM
            tb_product_category st
                RIGHT JOIN
            (SELECT
                mt.product_category_id AS m_product_category_id,
                    mt.parent AS m_parent,
                    mt.product_category_name AS m_product_category_name,
                    bt.product_category_id AS b_product_category_id,
                    bt.parent AS b_parent,
                    bt.product_category_name AS b_product_category_name
            FROM
                tb_product_category mt, (SELECT
                bt.product_category_id, bt.parent, bt.product_category_name
            FROM
                tb_product_category bt
            WHERE
                bt.parent = bt.product_category_id) bt
            WHERE
                bt.product_category_id = mt.parent
                    AND bt.product_category_id = COALESCE(?1, bt.product_category_id)
                    AND mt.product_category_id = COALESCE(?2, mt.product_category_id)
                    AND mt.product_category_id <> mt.parent) bmt ON bmt.m_product_category_id = st.parent
        WHERE
            st.product_category_id = COALESCE(?3, st.product_category_id)
        """,
            countQuery = """
                SELECT
                    count(*)
                FROM
                    tb_product_category
                WHERE
                    product_category_id = COALESCE(?1, product_category_id)
                 AND
                    product_category_id = COALESCE(?2, product_category_id)
                 AND
                    product_category_id = COALESCE(?3, product_category_id)
            """,
            nativeQuery = true)
    Page<MultipleCategoryQueryDtoInterface> findAllProductCategories(
            @Param("bigCategoryId") Long bigCategoryId,
            @Param("middleCategoryId") Long middleCategoryId,
            @Param("smallCategoryId") Long smallCategoryId,
            Pageable pageable);
}
