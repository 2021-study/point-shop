package product.demo.shop.util;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import product.demo.shop.common.logger.CommonLogger;

public class LoggerTest {

    @Test
    public void infoLoggerTest() {
        assertDoesNotThrow(()-> CommonLogger.info("Info Logger", LoggerTest.class));
    }
    @Test
    public void debugLoggerTest() {
        assertDoesNotThrow(()-> CommonLogger.debug("debug Logger", LoggerTest.class));
    }
    @Test
    public void warnLoggerTest() {
        assertDoesNotThrow(()-> CommonLogger.warn("warn Logger", LoggerTest.class));
    }
    @Test
    public void errorLoggerTest() {
        assertDoesNotThrow(()-> CommonLogger.error("error Logger", LoggerTest.class));
    }
}
