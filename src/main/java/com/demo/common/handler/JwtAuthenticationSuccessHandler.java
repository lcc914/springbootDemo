package com.demo.common.handler;

import com.alibaba.fastjson.JSON;
import com.demo.emuns.ResultEnum;
import com.demo.pojo.vo.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LiChangChen
 * @Date: 2020/10/18
 */
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.getWriter().write(JSON.toJSONString(Result.success(ResultEnum.LOGIN_SUCCESS)));
    }
}