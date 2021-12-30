package product.demo.shop.domain.product.controller;

import java.awt.print.Pageable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/")
    public SingleProductCategoryQueryDto findSingleCategory(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("categoryName") String categoryName) {

        // TODO : implement later
        return new SingleProductCategoryQueryDto(categoryId, categoryId, categoryName);
    }

    @GetMapping("/list")
    public Page<ProductCategoryQueryDto> findMultipleCategory(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("categoryName") String categoryName,
            final Pageable pageable) {

//        var result = new PageImpl<ProductCategoryQueryDto>(List.of(new ProductCategoryQueryDto()));

        // TODO : implement later
        return null;
    }
}
