package com.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Date: 2020/9/23
 *
 * @author: lcc
 */
@RestController
@RequestMapping("/api")
public class PageController {

    /**
     * 跳转到admin.html页面（需要登录，且需要ROLE_ADMIN角色）
     */
    @GetMapping("/toAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String toAdmin() {
        return "admin.html";
    }

    /**
     * 跳转到employee.html页面（需要登录，且需要ROLE_EMPLOYEE角色）
     */
    @GetMapping("/toEmployee")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String toEmployee() {
        return "employee.html";
    }

    /**
     * 跳转到employee.html页面（需要登录，但不需要角色）
     * 注意：虽然WebSecurityConfig中配置了/toUser不需要登录，但是这里配置的权限更小，因此，/toUser以这里的配置为准
     */
    @GetMapping("/toUser")
    @PreAuthorize("isAuthenticated()")
    public String toUser() {
        return "user.html";
    }

    /**
     * 跳转到home.html页面（需要登录，但不需要角色）
     * 注意：虽然这里配置了/toAbout不需要登录，但WebSecurityConfig中配置的权限更小，因此，/toAbout以WebSecurityConfig中配置的为准
     */
    @GetMapping("/toAbout")
    @PreAuthorize("permitAll")
    public String toAbout() {
        return "about.html";
    }


    /**
     * 跳转到home.html页面（不需要登录）
     */
    @GetMapping("/toHome")
    public String toHome() {
        return "home.html";
    }

    /**
     * 跳转到home.html页面（不需要登录）
     */
    @GetMapping("/login")
    public String toIndex() {
        return "static/login.html";
    }

}
