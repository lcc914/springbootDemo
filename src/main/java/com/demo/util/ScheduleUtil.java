package com.demo.util;


import org.quartz.*;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

/**
 * 定时器工具类
 *
 * @Author: LiChangChen
 * @Date: 2020/10/14
 */
public class ScheduleUtil {
    private static String JOB_NAME = "EXTJWEB_NAME";
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static String TRIGGER_NAME = "EXTJWEB_NAME";
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

    /**
     * @param scheduler:调度器
     * @param jobClass:任务
     * @param time:时间设置，CronExpression表达式
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     */
    public static void addJob(Scheduler scheduler, @SuppressWarnings("rawtypes") Class jobClass, String time) {
        addJob(scheduler, jobClass, time, JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);
    }

    /**
     * @param scheduler:调度器
     * @param jobClass:任务
     * @param time:时间设置，CronExpression表达式
     * @param jobName:任务名
     * @param jobGroupName:任务组名
     * @param triggerName:触发器名
     * @param triggerGroupName:触发器组名
     * @Description: 添加一个定时任务
     */
    public static void addJob(Scheduler scheduler, @SuppressWarnings("rawtypes") Class jobClass, String time,
                              String jobName, String jobGroupName, String triggerName, String triggerGroupName) {

        JobDetail job = newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        CronTrigger trigger = newTrigger().withIdentity(triggerName, triggerGroupName)
                .withSchedule(cronSchedule(time)).build();
        try {
            // 返回为 null 添加失败
            Date ft = scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param scheduler:调度器
     * @param time
     * @Description: 定义一个任务之后进行触发设定(使用默认的任务组名 ， 触发器名 ， 触发器组名)
     */
    @SuppressWarnings("rawtypes")
    public static void addJObLaterUse(Scheduler scheduler, @SuppressWarnings("rawtypes") Class jobClass, String time) {

        addJObLaterUse(scheduler, jobClass, time, JOB_NAME, JOB_GROUP_NAME);
    }

    /**
     * @param scheduler:调度器
     * @param time
     * @param jobName:任务名
     * @param jobGroupName:任务组名
     * @Description: 定义一个任务之后进行触发设定
     */
    @SuppressWarnings("rawtypes")
    public static void addJObLaterUse(Scheduler scheduler, @SuppressWarnings("rawtypes") Class jobClass, String time,
                                      String jobName, String jobGroupName) {

        JobDetail job = newJob(jobClass).withIdentity(jobName, jobGroupName).storeDurably().build();

        try {
            scheduler.addJob(job, false);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param scheduler:调度器
     * @param time
     * @Description: 对已存储的任务进行scheduling(使用默认的任务组名 ， 触发器名 ， 触发器组名)
     */
    @SuppressWarnings("rawtypes")
    public static void schedulingStoredJOb(Scheduler scheduler, @SuppressWarnings("rawtypes") Class jobClass, String time) {
        schedulingStoredJOb(scheduler, jobClass, time, JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);
    }

    /**
     * @param scheduler:调度器
     * @param time
     * @param jobName:任务名
     * @param jobGroupName:任务组名
     * @Description: 对已存储的任务进行scheduling
     */
    @SuppressWarnings("rawtypes")
    public static void schedulingStoredJOb(Scheduler scheduler, @SuppressWarnings("rawtypes") Class jobClass, String time,
                                           String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        Trigger trigger = newTrigger().withIdentity(triggerName, triggerGroupName).startNow()
                .forJob(jobKey(jobName, jobGroupName))
                .build();
        try {
            scheduler.scheduleJob(trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param sched:调度器
     * @param time
     * @Description: 修改一个任务的触发时间(使用默认的任务组名 ， 触发器名 ， 触发器组名)
     */
    @SuppressWarnings("rawtypes")
    public static void modifyJobTime(Scheduler sched, String time) {
        modifyJobTime(sched, TRIGGER_NAME, TRIGGER_GROUP_NAME, time);
    }

    /**
     * @param scheduler:调度器
     * @param triggerName
     * @param triggerGroupName
     * @param time
     * @Description: 修改一个任务的触发时间
     */
    public static void modifyJobTime(Scheduler scheduler, String triggerName, String triggerGroupName, String time) {
        Trigger trigger = newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronSchedule(time)).startNow().build();

        try {
            scheduler.rescheduleJob(triggerKey(triggerName, triggerGroupName), trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param scheduler:调度器
     * @Description: 修改一个任务(使用默认的任务组名 ， 任务名)
     */
    @SuppressWarnings("rawtypes")
    public static void modifyJob(Scheduler scheduler, @SuppressWarnings("rawtypes") Class jobClass) {
        modifyJob(scheduler, jobClass, JOB_NAME, JOB_GROUP_NAME);
    }

    /**
     * @param scheduler:调度器
     * @param jobName:任务名
     * @param jobGroupName:任务组名
     * @Description: 修改一个任务
     */
    public static void modifyJob(Scheduler scheduler, @SuppressWarnings("rawtypes") Class jobClass, String jobName, String jobGroupName) {
        JobDetail job1 = newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        try {
            scheduler.addJob(job1, true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param scheduler:调度器
     * @param triggerName
     * @param triggerGroupName
     * @Description: 删除一个任务的的trigger
     */
    public static void unSchedulingJob(Scheduler scheduler, String triggerName, String triggerGroupName) {
        try {
            scheduler.unscheduleJob(triggerKey(triggerName, triggerGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param scheduler:调度器
     * @param jobName
     * @Description: 移除一个任务，以及任务的所有trigger
     */
    public static void removeJob(Scheduler scheduler, String jobName, String jobGroupName) {
        try {
            scheduler.deleteJob(jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param scheduler:调度器
     * @Description:启动所有定时任务
     */
    public static void startJobs(Scheduler scheduler) {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param scheduler:调度器
     * @Description:关闭所有定时任务
     */
    public static void shutdownJobs(Scheduler scheduler) {
        try {
            if (!scheduler.isShutdown()) {
                //未传参或false：不等待执行完成便结束；true：等待任务执行完才结束
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
