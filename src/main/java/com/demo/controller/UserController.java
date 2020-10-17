package com.demo.controller;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.demo.pojo.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lcc
 * @since 2020-10-11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public R test(@RequestBody User user) {
        user.setUsername("张三" + RandomUtil.randomNumbers(3));
        user.setPassword("test");
        userService.save(user);
        return R.ok("");
    }

    @GetMapping("/login")
    public R login() {
        return R.ok("");
    }


}
