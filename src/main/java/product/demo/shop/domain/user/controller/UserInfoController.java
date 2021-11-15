package product.demo.shop.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.demo.shop.domain.user.dto.UserInfoDto;
import product.demo.shop.domain.user.service.UserInfoService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserInfoController.USER_INFO_API_PATH)
public class UserInfoController {
    public static final String USER_INFO_API_PATH = "/api/v1/user/info";

    private final UserInfoService userInfoService;

    @GetMapping("/{userInfoId}")
    public UserInfoDto findComprehensiveUserInfo(@Valid @PathVariable long userInfoId) {
        return userInfoService.findMyUserInformation(userInfoId);
    }
}
