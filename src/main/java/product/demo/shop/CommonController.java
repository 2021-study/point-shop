package product.demo.shop;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO : 로그인 성공 여부 테스트를 위한 API 입니다.
@RestController
@RequestMapping(CommonController.DEFAULT_PATH)
public class CommonController {
    public static final String DEFAULT_PATH = "/api/main";

    @GetMapping("")
    public String mainLoginTest(){
        SecurityContext secContext = SecurityContextHolder.getContext();

        return "Hello " + secContext.getAuthentication().getName();
    }
}
