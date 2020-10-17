package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.LogTable;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lcc
 * @since 2020-10-14
 */
public interface LogTableService extends IService<LogTable> {

    /**
     * te
     *
     * @param logTable
     * @return
     */
    Integer testSave(LogTable logTable);

}
