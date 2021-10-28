package product.demo.shop.common.email;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MockMailSender implements MailSender {

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        try {
            System.out.println("===========================");
            System.out.println("Processing Mail......");
            System.out.println("===========================");
            Thread.sleep(2_000); // send mail을 처리하는데 2초의 지연이 발생한다고 가정.

            System.out.println("Receiver Address : " + simpleMessage.getTo());
            System.out.println("From Address : " + simpleMessage.getFrom());
            System.out.println("Title : " + simpleMessage.getSubject());
            System.out.println("Content : " + simpleMessage.getText());

            // 예외 테스트를 위해 text에 특정 문자열이 오면 예외를 던지도록 함.
            if(simpleMessage.getText().equals("EMAIL_SERVER_AUTH_FAIL")){
                throw new MailAuthenticationException("EMAIL_SERVER_AUTH_FAIL");
            }

            System.out.println("===========================");
            System.out.println("Mail Send Success......");
            System.out.println("===========================");
//            mailMessage.setTo(emailParameter.getReceiverEmailAddress());
//            mailMessage.setFrom(senderEmailAddress);
//            mailMessage.setSubject(emailParameter.getTitle());
//            mailMessage.setText(emailParameter.getContent());


        } catch (InterruptedException e) {
            e.printStackTrace(); // 운영 소스에 printStackTrace 호출을 자제 합니다.
        } catch(Exception ex){
            System.out.println("===========================");
            System.out.println("Mail Send Failed......");
            System.out.println("===========================");
            throw ex;
        }
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace(); // 운영 소스에 printStackTrace 호출을 자제 합니다.
        }
    }
}
