package product.demo.shop.domain.user.service;

import product.demo.shop.domain.user.dto.UserInfoDto;

public interface UserInfoService {
    UserInfoDto findMyUserInformation(long userInfoId);
}
