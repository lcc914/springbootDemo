package com.demo.controller;


import com.demo.pojo.Account;
import com.demo.pojo.vo.Result;
import com.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 账号 前端控制器
 * </p>
 *
 * @author lcc
 * @since 2020-10-17
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("list")
    public Result listAccount() {
        return Result.success(accountService.list());
    }

    @PostMapping("")
    public Result addAccount(@RequestBody Account account) {
        accountService.save(account);
        return Result.success(accountService.list());
    }

}
