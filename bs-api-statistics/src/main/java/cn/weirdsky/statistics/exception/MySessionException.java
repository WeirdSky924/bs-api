package cn.weirdsky.statistics.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MySessionException extends RuntimeException {

    private static final long serialVersionUID = 254568372772352481L;
    private Integer code;

    public MySessionException(Integer code) {
        this.code = code;
    }

    public MySessionException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public MySessionException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public MySessionException(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }

    public MySessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
