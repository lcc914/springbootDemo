package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.LogTableMapper;
import com.demo.pojo.LogTable;
import com.demo.service.LogTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lcc
 * @since 2020-10-14
 */
@Service
public class LogTableServiceImpl extends ServiceImpl<LogTableMapper, LogTable> implements LogTableService {
    @Resource
    private LogTableMapper logTableMapper;

    @Override
    public Integer testSave(LogTable logTable) {
        return logTableMapper.insert(logTable);
    }
}
