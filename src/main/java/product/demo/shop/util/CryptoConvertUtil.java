package product.demo.shop.util;

import product.demo.shop.common.exception.CommonErrorCode;
import product.demo.shop.common.exception.CommonException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptoConvertUtil {

    public static final String CRYPTO_ALGORITHM = "AES";
    public static final String KEY = "cG9pbnRzaG9wcG9p"; // KEY는 16, 24, 32 Byte이어야 한다.

    public static String encrypt(String pwd) {
        String encodedPwd = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(CRYPTO_ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(pwd.getBytes());
            encodedPwd = Base64.getEncoder().encodeToString(encVal);

        } catch (Exception e) {
            // TODO : 이 부분 테스트 하는 법 아시는분?
            throw new CommonException(CommonErrorCode.ENCRYPTION_ERROR); // 암호화 처리에 실패하였습니다.
        }
        return encodedPwd;
    }

    public static String decrypt(String encryptedData) {
        String decodedPWD = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(CRYPTO_ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            decodedPWD = new String(decValue);

        } catch (Exception e) {
            // TODO : 이 부분 테스트 하는 법 아시는분?
            throw new CommonException(CommonErrorCode.DECRYPTION_ERROR); // 복호화 처리에 실패하였습니다.
        }
        return decodedPWD;
    }

    private static Key generateKey() {
        byte[] byteKeyValue = KEY.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(byteKeyValue, CRYPTO_ALGORITHM);
    }
}
