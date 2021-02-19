package com.demo.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.demo.job.UploadTask;
import com.demo.util.ScheduleUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: LiChangChen
 * @Date: 2020/10/14
 */
@RestController
public class JobController {
    Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private Scheduler scheduler;

    @GetMapping("/createJob")
    public R createJob() throws SchedulerException {
        //cron表达式
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/8 * * * * ?");
        //根据name 和group获取当前trgger 的身份
        TriggerKey triggerKey = TriggerKey.triggerKey("cj", "123");
        CronTrigger triggerOld = null;
        try {
            //获取 触发器的信息
            triggerOld = (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        if (triggerOld == null) {
            //将job加入到jobDetail中
            JobDetail jobDetail = JobBuilder.newJob(UploadTask.class).withIdentity("cj", "123").build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("cj", "123").withSchedule(cronScheduleBuilder).build();
            //执行任务
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            System.out.println("当前job已存在--------------------------------------------");
        }
        return R.ok("");
    }

    @GetMapping("/createJob2")
    public R createJob2() throws SchedulerException {
        logger.error("test");
        ScheduleUtil.addJob(scheduler, UploadTask.class, DateUtil.format(new Date(System.currentTimeMillis() + 5000), "ss mm HH dd MM ? yyyy"));
        return R.ok("");
    }

}
