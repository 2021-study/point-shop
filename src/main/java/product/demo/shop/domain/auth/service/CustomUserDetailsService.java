package product.demo.shop.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import product.demo.shop.domain.auth.exception.PointShopAuthErrorCode;
import product.demo.shop.domain.auth.exception.PointShopAuthException;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.entity.enums.UserStatusType;
import product.demo.shop.domain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userAccountId) throws UsernameNotFoundException {
        return userRepository
                .findByUserAccountId(userAccountId)
                .map(
                        user -> {
                            if (!user.getUserStatus().equals(UserStatusType.VERIFIED)) {
                                throw new PointShopAuthException(
                                        PointShopAuthErrorCode
                                                .NOT_YET_EMAIL_VERIFIED); // email 미인증.
                            }
                            return makeUserDetail(user);
                        })
                .orElseThrow(
                        () -> new UsernameNotFoundException("[" + userAccountId + "]는 존재하지 않습니다."));
    }

    private User makeUserDetail(UserEntity userEntity) {
        // 일단 권한은 USER로 하드코딩 박아놓는다.
        var grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        return new User(
                userEntity.getUserAccountId(), userEntity.getPassword(), List.of(grantedAuthority));
    }
}
