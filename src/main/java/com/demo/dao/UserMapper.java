package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lcc
 * @since 2020-10-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
