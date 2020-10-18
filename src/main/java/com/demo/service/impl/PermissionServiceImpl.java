package com.demo.service.Impl;

import com.demo.pojo.Permission;
import com.demo.dao.PermissionMapper;
import com.demo.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author lcc
 * @since 2020-10-17
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
