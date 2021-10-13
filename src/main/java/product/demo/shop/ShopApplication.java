package product.demo.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import product.demo.shop.util.CryptoConvertUtil;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShopApplication.class, args);

        System.out.println("myshop : " + CryptoConvertUtil.encrypt("myshop"));
        System.out.println("point : " + CryptoConvertUtil.encrypt("point"));
    }

}
