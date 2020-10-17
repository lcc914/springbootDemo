package com.demo.job;

import com.demo.service.UserService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: LiChangChen
 * @Date: 2020/10/14
 */
@Component
@DisallowConcurrentExecution
public class UploadTask extends QuartzJobBean {
    @Autowired
    private UserService userService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(new Date() + "任务开始------------------------------------");
        System.out.println(userService.count());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new Date() + "任务结束------------------------------------");
    }
}
