package product.demo.shop.common.converter;

import product.demo.shop.util.CryptoConvertUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String s) {
        return CryptoConvertUtil.encrypt(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return CryptoConvertUtil.decrypt(s);
    }
}
