package com.demo.service.Impl;

import com.demo.pojo.UserGroup;
import com.demo.dao.UserGroupMapper;
import com.demo.service.UserGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组 服务实现类
 * </p>
 *
 * @author lcc
 * @since 2020-10-17
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {

}
