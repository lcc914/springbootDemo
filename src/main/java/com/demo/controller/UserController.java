package com.demo.controller;


import com.demo.pojo.User;
import com.demo.pojo.vo.Result;
import com.demo.service.UserService;
import com.demo.util.JwtTokenUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author lcc
 * @since 2020-10-17
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserService userService;

    @ApiOperation(value = "", notes = "{\n" +
            "  \"username\": \"13866995555\",\n" +
            "  \"password\": \"111111\"\n" +
            "}")
    @GetMapping(value = "/login")
    @ResponseBody
    public Result login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        // 系统登录认证
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            return Result.error("登录失败");
        }
        String token = JwtTokenUtils.generateToken(authentication);
        return Result.success("登录成功", token);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("permitAll")
    public Result add(@RequestBody User user) {
        // 系统登录认证
        List<User> list = userService.list();
        System.out.println("do---------------------");
        return Result.success(list);
    }


}
