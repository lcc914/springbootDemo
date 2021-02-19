package com.demo.controller;


import com.demo.common.config.swagger.ApiJsonObject;
import com.demo.common.config.swagger.ApiJsonProperty;
import com.demo.pojo.User;
import com.demo.pojo.vo.Result;
import com.demo.service.UserService;
import com.demo.util.JwtTokenUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    @Resource
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "", notes = "{\n" +
            "  \"username\": \"13866995555\",\n" +
            "  \"password\": \"111111\"\n" +
            "}")
    @PostMapping(value = "/login")
    @ResponseBody
    public Result login(     @ApiJsonObject(name = "login_model", value = {
            @ApiJsonProperty(key = "userId", example = "1222221", description = "用户id"),
            @ApiJsonProperty(key = "articleId", example = "12", description = "文章id"),
            @ApiJsonProperty(key = "type", example = "1", required = true, description = "type 1.点赞  2.收藏 3 足迹  4 分享")
    })@RequestBody Map<String, String> user, HttpServletRequest request) {
        // 系统登录认证
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.get("username"), user.get("password")));
        } catch (Exception e) {
            return Result.error("登录失败");
        }
        String token = JwtTokenUtils.generateToken(authentication);
        return Result.success("登录成功", token);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
//    @PreAuthorize("permitAll")
    public Result add(@RequestBody User user) {
        // 系统登录认证
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        List<User> list = userService.list();
        return Result.success(list);
    }


}
