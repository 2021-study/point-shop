package product.demo.shop.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import product.demo.shop.auth.exception.PointShopAuthErrorCode;
import product.demo.shop.auth.exception.PointShopAuthException;
import product.demo.shop.domain.user.entity.UserEntity;
import product.demo.shop.domain.user.entity.enums.UserStatusType;
import product.demo.shop.domain.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class CustomUserDetailServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock private UserRepository userRepository;

    @InjectMocks private CustomUserDetailsService userDetailsService;

    @Test
    @DisplayName("등록되어 있는 유저에 대한 로그인 성공처리(ROLE_USER)")
    public void successInHouseLogin() throws Exception {

        var testUser =
                UserEntity.builder()
                        .userInfoId(1L)
                        .userGradeId(1L)
                        .userAccountId("jay")
                        .name("hwang")
                        .email("dlswp113@gmail.com")
                        .phone("010-0000-0000")
                        .address("사랑시고백구행복동")
                        .password("1234")
                        .userStatus(UserStatusType.VERIFIED) // Verified된 유저만 로그인 처리.
                        .build();

        when(userRepository.findByUserAccountId(any())).thenReturn(Optional.of(testUser));

        var resUserDetails = this.userDetailsService.loadUserByUsername("jay");
        log.info(objectMapper.writeValueAsString(resUserDetails));
        assertTrue(
                resUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    @DisplayName("VERIFIED 상태가 아닌유저(==이메일 미인증) 유저는 팅겨낸다.")
    public void failInHouseLoginForNoEmailVerification() throws Exception {
        var testUser =
                UserEntity.builder()
                        .userInfoId(1L)
                        .userGradeId(1L)
                        .userAccountId("jay")
                        .name("hwang")
                        .email("dlswp113@gmail.com")
                        .phone("010-0000-0000")
                        .address("사랑시고백구행복동")
                        .password("1234")
                        .userStatus(UserStatusType.REGISTERED_NOT_CONFIRMED) // 메일 미인증 유저
                        .build();

        when(userRepository.findByUserAccountId(any())).thenReturn(Optional.of(testUser));

        var exception =
                assertThrows(
                        PointShopAuthException.class,
                        () -> this.userDetailsService.loadUserByUsername("jay"));
        log.info(objectMapper.writeValueAsString(exception));
        assertEquals(
                PointShopAuthErrorCode.NOT_YET_EMAIL_VERIFIED.getErrorMessage(),
                exception.getErrorMessage());
    }

    @Test
    @DisplayName("DB에 존재하지 않는 유저는 팅겨 낸다.")
    public void failInHouseLoginForUserNotFoundInDB() throws Exception {
        when(userRepository.findByUserAccountId(any())).thenReturn(Optional.empty());

        var exception =
                assertThrows(
                        UsernameNotFoundException.class,
                        () -> this.userDetailsService.loadUserByUsername("jay"));
        log.info(exception.getMessage());

        //        assertEquals(PointShopAuthErrorCode.USER_NOT_EXISTS.getErrorMessage(),
        // exception.getErrorMessage());
    }
}
