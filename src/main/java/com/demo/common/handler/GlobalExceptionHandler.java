package com.demo.common.handler;

import com.baomidou.mybatisplus.extension.api.R;
import com.demo.emuns.ResultEnum;
import com.demo.pojo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @Author: LiChangChen
 * @Date: 2020/10/13
 */
@ControllerAdvice
@Slf4j
@Controller
public class GlobalExceptionHandler {
    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class,
            ServletRequestBindingException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Result handleHttpMessageNotReadableException(Exception e) {
        e.printStackTrace();
        return Result.error(ResultEnum.METHOD_NOT_ALLOWED.getMessage());
    }

    /**
     * 404 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NoHandlerFoundException.class})
    public String handleHttpMessageNotFundException() {
        return "/error";
    }

    /**
     * 405 - Method Not Allowed
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return Result.error(ResultEnum.METHOD_NOT_ALLOWED.getMessage());
    }

    /**
     * 其他全局异常在此捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e) {
        e.printStackTrace();
        return Result.error(ResultEnum.METHOD_NOT_ALLOWED.getMessage());
    }

    /**
     * E
     */
    @ExceptionHandler(Exception.class)
    public Result allException(Exception e) {
        e.printStackTrace();
        return Result.error(ResultEnum.METHOD_NOT_ALLOWED.getMessage());
    }
}
