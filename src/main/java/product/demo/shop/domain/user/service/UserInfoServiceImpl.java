package product.demo.shop.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import product.demo.shop.domain.user.dto.UserInfoDto;
import product.demo.shop.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserRepository userRepository;

    @Override
    public UserInfoDto findMyUserInformation(long userInfoId) {
        return this.userRepository.findMyUserComprehensiveInfo(userInfoId);
    }
}
