package product.demo.shop.domain.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //TODO 유저 로그인을 위해 UserDetails를 상속받은 클래스를 만든 후 로직에 맞게 구현 필요합니다.
        return null;
    }
}
