package product.demo.shop.common.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailParameter {

    // TODO : Spring validation이 merge가 되면 @Email 어노테이션을 활용해보는것도 좋을 듯.
    // TODO : 이메일 입력 검증을 자체 구현 한다고 하면 정규식(Regex)를 응용해서 활용.
    private String receiverEmailAddress;
    private String title;
    private String content;
}
