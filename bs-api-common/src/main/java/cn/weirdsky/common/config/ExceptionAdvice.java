package cn.weirdsky.common.config;

import cn.weirdsky.common.entity.Code;
import cn.weirdsky.common.entity.R;
import cn.weirdsky.common.exception.MySessionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MySessionException.class) // 异常类型
    public Object exceptionAdvice(MySessionException e) {
        return R.error(Code.BUSINESS_ERR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Object otherExceptionAdvice(Exception e) {
        e.printStackTrace();
        return R.error(Code.SYSTEM_ERR, e.getMessage());
    }

}