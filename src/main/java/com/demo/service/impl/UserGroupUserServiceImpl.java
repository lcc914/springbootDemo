package com.demo.service.Impl;

import com.demo.pojo.UserGroupUser;
import com.demo.dao.UserGroupUserMapper;
import com.demo.service.UserGroupUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组成员 服务实现类
 * </p>
 *
 * @author lcc
 * @since 2020-10-17
 */
@Service
public class UserGroupUserServiceImpl extends ServiceImpl<UserGroupUserMapper, UserGroupUser> implements UserGroupUserService {

}
