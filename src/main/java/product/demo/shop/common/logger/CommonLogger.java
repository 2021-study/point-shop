package product.demo.shop.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonLogger {

    public static void info(String message, Class<?> classType) {
        Logger logger = LoggerFactory.getLogger(classType);
        logger.info(message);
    }

    public static void debug(String message, Class<?> classType) {
        Logger logger = LoggerFactory.getLogger(classType);
        logger.debug(message);
    }

    public static void error(String message, Class<?> classType) {
        Logger logger = LoggerFactory.getLogger(classType);
        logger.error(message);
    }

    public static void warn(String message, Class<?> classType) {
        Logger logger = LoggerFactory.getLogger(classType);
        logger.warn(message);
    }
}
