package team.tomato.springbootdemo.common.exception;

import lombok.Data;

/**
 * Created by Iverson on 2019/7/4.
 */
@Data
public class BusinessException extends RuntimeException {
    private int code = 500;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }
}
