package product.demo.shop.domain.product.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import product.demo.shop.domain.product.dto.query.ProductCategoryQueryDto;
import product.demo.shop.domain.product.dto.query.SingleProductCategoryQueryDto;

@RestController
@RequestMapping(ProductCategoryController.PRODUCT_CATEGORY_PATH)
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryController {

    public static final String PRODUCT_CATEGORY_PATH = "/api/v1/category";

    @GetMapping("")
    public SingleProductCategoryQueryDto findSingleCategory(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("categoryName") String categoryName) {

        // TODO : implement later
        return new SingleProductCategoryQueryDto(categoryId, categoryId, categoryName);
    }

    @PostMapping("/list")
    public PageImpl<ProductCategoryQueryDto> findMultipleCategory(
            @RequestBody ProductCategoryQueryDto requestDto, final Pageable pageable) {

        var result =
                new PageImpl(
                        List.of(
                                new ProductCategoryQueryDto(
                                        1L, 1L, "농산물", 2L, 1L, "청과", 3L, 2L, "수박")));

        // TODO : implement later
        return result;
    }
}
