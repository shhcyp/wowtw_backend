package cn.wowtw_backend.exception;

import cn.wowtw_backend.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result exception(Exception exception) {
        exception.printStackTrace();
        return Result.fail("发生错误", exception.getMessage());
    }

    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Exception e) {
        return "error/404"; // 返回一个自定义的404页面，或者返回一个简单的字符串
    }
}
