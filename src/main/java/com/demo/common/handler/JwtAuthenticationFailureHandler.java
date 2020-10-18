package com.demo.common.handler;

import com.alibaba.fastjson.JSON;
import com.demo.pojo.vo.Result;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LiChangChen
 * @Date: 2020/10/18
 */
@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, AuthenticationException exception)
            throws IOException {
        String message = "";
        if (exception instanceof UsernameNotFoundException) {
            message = "用户不存在!";
        } else if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误!";
        } else if (exception instanceof LockedException) {
            message = "用户已被锁定!";
        } else if (exception instanceof DisabledException) {
            message = "用户不可用!";
        } else if (exception instanceof AccountExpiredException) {
            message = "账户已过期!";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "用户密码已过期!";
        } else {
            message = "认证失败，请联系网站管理员！";
        }
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.error(message)));
    }
}
