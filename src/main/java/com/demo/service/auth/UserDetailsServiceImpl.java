package com.demo.service.auth;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.config.security.JwtUserDetails;
import com.demo.pojo.*;
import com.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
    private UserGroupRoleService userGroupRoleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserGroupUserService userGroupUserService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

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
        HashSet<Long> allRoleIds = new HashSet<>();
        List<Permission> permissions = null;
        //获取人下面的所有角色
        List<UserRole> userRoles = userRoleService.list(new QueryWrapper<UserRole>().eq(UserRole.USER_ID, user.getId()).select(UserRole.ROLE_ID));
        //获取所有组
        List<UserGroupUser> userGroupUsers = userGroupUserService.list(new QueryWrapper<UserGroupUser>().eq(UserGroupUser.USER_ID, user.getId()).select(UserGroupUser.USER_GROUP_ID));
        List<Long> userGroupUserList = new ArrayList<>();
        userGroupUsers.forEach(userGroupUser -> userGroupUserList.add(userGroupUser.getUserGroupId()));
        //获取组下面的所有角色
        if (CollectionUtil.isNotEmpty(userGroupUserList)) {
            List<UserGroupRole> userGroupRoles = userGroupRoleService.list(new QueryWrapper<UserGroupRole>().in(UserGroupRole.USER_GROUP_ID, userGroupUserList).select(UserGroupRole.ROLE_ID));
            userGroupRoles.forEach(role -> allRoleIds.add(role.getRoleId()));
        }
        userRoles.forEach(role -> allRoleIds.add(role.getRoleId()));
        //获取角色下的所有权限
        if (CollectionUtil.isNotEmpty(allRoleIds)) {
            List<RolePermission> rolePermissions = rolePermissionService.list(new QueryWrapper<RolePermission>().in(RolePermission.ROLE_ID, allRoleIds).select(RolePermission.PERMISSION_ID));
            List<Long> rolePermissionList = new ArrayList<>();
            rolePermissions.forEach(rolePermission -> rolePermissionList.add(rolePermission.getPermissionId()));
            if (CollectionUtil.isNotEmpty(rolePermissionList)) {
                //树状图
                permissions = permissionService.listByIds(rolePermissionList);
            }
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Permission permission : CollectionUtil.emptyIfNull(permissions)) {
            //封装用户信息和角色信息到SecurityContextHolder全局缓存中
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
        return new JwtUserDetails(username, user.getPassword(), grantedAuthorities);
    }

}
