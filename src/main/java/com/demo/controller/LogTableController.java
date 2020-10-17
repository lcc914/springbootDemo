package com.demo.controller;


import com.demo.pojo.LogTable;
import com.demo.pojo.vo.Result;
import com.demo.service.LogTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lcc
 * @since 2020-10-14
 */
@RestController
@RequestMapping("/logTable")
public class LogTableController {

    @Autowired
    private LogTableService logTableService;

    @GetMapping("")
    public Result getAll() {
        List<LogTable> list = logTableService.list();
        LogTable logTable = new LogTable();
        logTable.setOperateDate(new Date());
        Integer integer = logTableService.testSave(logTable);
        return Result.error();
    }

}
