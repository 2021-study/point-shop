package product.demo.shop.domain.user.repository.custom;

import product.demo.shop.domain.user.dto.UserInfoDto;

public interface CustomUserRepository {

    UserInfoDto findUserWithUserGradeInfo(Long userId);
}
