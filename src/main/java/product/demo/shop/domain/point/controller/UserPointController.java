package product.demo.shop.domain.point.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.demo.shop.domain.point.aggregate.UserPointAggregateService;
import product.demo.shop.domain.point.aggregate.dto.UserPointResponseDetail;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = UserPointController.USER_POINT_PATH)
public class UserPointController {

    public static final String USER_POINT_PATH = "/api/v1/user-point";
    private final UserPointAggregateService userPointAggregateService;

    @GetMapping("/{userInfoId}")
    public Page<UserPointResponseDetail> searchUserPointDetails(
            @PathVariable long userInfoId, final Pageable pageable) {
        return userPointAggregateService.searchUserPointDetails(userInfoId, pageable);
    }
}
