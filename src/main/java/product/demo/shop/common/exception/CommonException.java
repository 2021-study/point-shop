package product.demo.shop.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException{

    String message;
    HttpStatus errorStatus;
    int errorCode;
    String errorMessage;
    String description;
    Throwable cause;

    public CommonException(String message){
        super(message);
        this.message = message;
    }

    public CommonException(String message, Throwable throwable){
        super(message, throwable);
        this.message = message;
        this.cause = throwable;
    }

    public CommonException(ErrorCode errorCode){
        super("[ErrorCode] : " + errorCode.getErrorStatus() + "\n"
                + "[ErrorMessage] : " + errorCode.getErrorMessage() + "\n");
        this.errorCode = errorCode.getErrorStatus().value();
        this.errorMessage = errorCode.getErrorMessage();
        this.errorStatus = errorCode.getErrorStatus();
        this.message = super.getMessage();
    }

    public CommonException(ErrorCode errorCode, Throwable throwable){
        super("[ErrorCode] : " + errorCode.getErrorStatus() + "\n"
                + "[ErrorMessage] : " + errorCode.getErrorMessage() + "\n", throwable);
        this.errorCode = errorCode.getErrorStatus().value();
        this.errorMessage = errorCode.getErrorMessage();
        this.errorStatus = errorCode.getErrorStatus();
        this.message = super.getMessage();
        this.cause = super.getCause();
    }

    public CommonException(ErrorCode errorCode, String description){
        super("[ErrorCode] : " + errorCode.getErrorStatus() + "\n"
                + "[ErrorMessage] : " + errorCode.getErrorMessage() + "\n"
               + "[Descirption] : " + description + "\n");
        this.errorCode = errorCode.getErrorStatus().value();
        this.description =description;
        this.errorMessage = errorCode.getErrorMessage();
        this.errorStatus = errorCode.getErrorStatus();
        this.message = super.getMessage();
    }

    public CommonException(ErrorCode errorCode, String description, Throwable throwable){
        super("[ErrorCode] : " + errorCode.getErrorStatus() + "\n"
                + "[ErrorMessage] : " + errorCode.getErrorMessage() + "\n"
                + "[Descirption] : " + description + "\n", throwable);
        this.errorCode = errorCode.getErrorStatus().value();
        this.description = description;
        this.cause = throwable;
        this.errorMessage = errorCode.getErrorMessage();
        this.errorStatus = errorCode.getErrorStatus();
        this.message = super.getMessage();
    }
}
