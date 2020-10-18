package com.demo.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.config.security.JwtUserDetails;
import com.demo.pojo.Role;
import com.demo.pojo.User;
import com.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 2020/9/23
 *
 * @author: lcc
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private UserGroupRoleService userGroupRoleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户名获取认证用户信息
     *
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(new QueryWrapper<User>().eq(User.MOBILE, username));
        /**
         * 根据用户名查找用户信息
         */
        if (user == null) {
            throw new UsernameNotFoundException(String.format("用户'%s'不存在", username));
        }
        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        List<Role> list = roleService.list();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : list) {
            //封装用户信息和角色信息到SecurityContextHolder全局缓存中
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new JwtUserDetails(username, user.getPassword(), grantedAuthorities);
    }

}
