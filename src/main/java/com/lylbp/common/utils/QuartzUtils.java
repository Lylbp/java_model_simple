package com.lylbp.common.utils;


import java.util.Date;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

/**
 * Quartz工具类
 *
 * @author weiwenbin
 * @date : 2020/10/16 下午4:57
 */
@Slf4j
public class QuartzUtils {
    public static final String JOB_NAME_PRE = "JOB_NAME_";
    public static final String JOB_GROUP_PRE = "JOB_GROUP_";
    public static final String TRIGGER_NAME_PRE = "TRIGGER_NAME_";
    public static final String TRIGGER_GROUP_PRE = "TRIGGER_GROUP_";

    private static Job getClass(String classname) throws SchedulerException {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classname);
            return (Job) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    /**
     * 添加定时任务
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类吗
     * @param time         时间设置，CronExpression表达式
     * @return Boolean
     */
    public static Boolean addJob(Scheduler scheduler, String jobClassName, String time) throws SchedulerException {
        return addJob(
                scheduler,
                jobClassName,
                time,
                JOB_NAME_PRE + jobClassName,
                JOB_GROUP_PRE + jobClassName,
                TRIGGER_NAME_PRE + jobClassName,
                TRIGGER_GROUP_PRE + jobClassName
        );
    }

    /**
     * 添加一个定时任务
     *
     * @param scheduler        调度器
     * @param jobClassName     任务类名
     * @param time             时间设置，CronExpression表达式
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @return Boolean
     */
    public static Boolean addJob(Scheduler scheduler, String jobClassName, String time, String jobName,
                                 String jobGroupName, String triggerName, String triggerGroupName)
            throws SchedulerException {
        Class<? extends Job> jobClazz = getClass(jobClassName).getClass();
        //启动调度器
        scheduler.start();
        //构造任务
        JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                .withIdentity(jobName, jobGroupName)
                .build();

        //构造任务触发器
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
        try {
            //将作业添加到调度器,返回为null添加失败
            Date date = scheduler.scheduleJob(jobDetail, trigger);
            return null != date;
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 只新增一个任务
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类名
     * @return Boolean
     */
    public static Boolean addJobLaterUse(Scheduler scheduler, String jobClassName) throws SchedulerException {
        return addJobLaterUse(
                scheduler,
                jobClassName,
                JOB_NAME_PRE + jobClassName,
                JOB_GROUP_PRE + jobClassName
        );
    }

    /**
     * 只新增一个任务
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类名
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     * @return Boolean
     */
    public static Boolean addJobLaterUse(Scheduler scheduler, String jobClassName, String jobName,
                                         String jobGroupName) throws SchedulerException {
        Class<? extends Job> jobClazz = getClass(jobClassName).getClass();
        //构造任务
        JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                .withIdentity(jobName, jobGroupName)
                .storeDurably()
                .build();
        try {
            scheduler.addJob(jobDetail, false);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 对已存储的作业添加到调度器
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类名
     * @return Boolean
     */
    public static Boolean schedulingStoredJob(Scheduler scheduler, String jobClassName) {
        return schedulingStoredJob(scheduler, JOB_NAME_PRE + jobClassName,
                JOB_GROUP_PRE + jobClassName,
                TRIGGER_NAME_PRE + jobClassName,
                TRIGGER_GROUP_PRE + jobClassName);
    }

    /**
     * 对已存储的作业添加到调度器
     *
     * @param scheduler        调度器
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @return Boolean
     */
    public static Boolean schedulingStoredJob(Scheduler scheduler, String jobName, String jobGroupName, String triggerName,
                                              String triggerGroupName) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .startNow()
                .forJob(JobKey.jobKey(jobName, jobGroupName))
                .build();
        try {
            //将作业添加到调度器,返回为null添加失败
            if (null == scheduler.scheduleJob(trigger)) {
                return false;
            }
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改任务的触发时间
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类名
     * @param time         时间设置，CronExpression表达式
     * @return Boolean
     */
    public static Boolean modifyJobTime(Scheduler scheduler, String jobClassName, String time) {
        return modifyJobTime(
                scheduler,
                TRIGGER_NAME_PRE + jobClassName,
                TRIGGER_GROUP_PRE + jobClassName,
                time
        );
    }

    /**
     * 修改任务的触发时间
     *
     * @param scheduler        调度器
     * @param time             时间设置，CronExpression表达式
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @return Boolean
     */
    public static Boolean modifyJobTime(Scheduler scheduler, String triggerName, String triggerGroupName, String time) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(time))
                .startNow()
                .build();

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            //将作业添加到调度器,返回为null添加失败
            return null != scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 先删除再新增一个任务
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类名
     * @param time         时间设置，CronExpression表达式
     * @return Boolean
     */
    public static Boolean deleteAndAddJob(Scheduler scheduler, String jobClassName, String time) throws SchedulerException {
        removeJob(scheduler, jobClassName);
        return addJob(scheduler, jobClassName, time);
    }

    /**
     * 暂停任务
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类名
     * @return Boolean
     */
    public static Boolean pauseJob(Scheduler scheduler, String jobClassName) {
        return pauseJob(scheduler, JOB_NAME_PRE + jobClassName, JOB_GROUP_PRE + jobClassName);
    }

    /**
     * 暂停任务
     *
     * @param scheduler    调度器
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     * @return Boolean
     */
    public static Boolean pauseJob(Scheduler scheduler, String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 恢复作业
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类名
     * @return Boolean
     */
    public static Boolean resumeJob(Scheduler scheduler, String jobClassName) {
        return resumeJob(scheduler, JOB_NAME_PRE + jobClassName, JOB_GROUP_PRE + jobClassName);
    }

    /**
     * 恢复作业
     *
     * @param scheduler    调度器
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     * @return Boolean
     */
    public static Boolean resumeJob(Scheduler scheduler, String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 移除一个任务
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类名
     * @return Boolean
     */
    public static Boolean removeJob(Scheduler scheduler, String jobClassName) {
        return removeJob(
                scheduler,
                JOB_NAME_PRE + jobClassName,
                JOB_GROUP_PRE + jobClassName,
                TRIGGER_NAME_PRE + jobClassName,
                TRIGGER_GROUP_PRE + jobClassName
        );
    }

    /**
     * 删除一个任务的的trigger
     *
     * @param scheduler        调度器
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @return Boolean
     */
    public static Boolean removeJob(Scheduler scheduler, String jobName, String jobGroupName,
                                    String triggerName, String triggerGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            //停止触发器
            scheduler.pauseTrigger(triggerKey);
            //移除触发器
            boolean unscheduleJobSuccess = scheduler.unscheduleJob(triggerKey);
            //删除任务
            boolean deleteJobSuccess = scheduler.deleteJob(jobKey);

            return unscheduleJobSuccess && deleteJobSuccess;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 启动所有定时任务
     *
     * @param scheduler 调度器
     * @return Boolean
     */
    public static Boolean startJobs(Scheduler scheduler) {
        try {
            scheduler.start();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 关闭所有定时任务
     *
     * @param scheduler    调度器
     * @param isWaitFinish 是否等待任务执行完才结束(false：不等待执行完成便结束；true：等待任务执行完才结束)
     * @return Boolean
     */
    public static Boolean shutdownJobs(Scheduler scheduler, Boolean isWaitFinish) {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown(isWaitFinish);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 立即执行
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类名
     * @return Boolean
     */
    public static Boolean runNow(Scheduler scheduler, String jobClassName) {
        return runNow(scheduler, JOB_NAME_PRE + jobClassName, JOB_GROUP_PRE + jobClassName);
    }

    /**
     * 立即执行
     *
     * @param scheduler    调度器
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     * @return Boolean
     */
    public static Boolean runNow(Scheduler scheduler, String jobName, String jobGroupName) {
        try {
            scheduler.triggerJob(JobKey.jobKey(jobName, jobGroupName));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 获取系统中已添加的所有任务
     *
     * @param scheduler 调度器
     * @return Set<JobKey>
     */
    public static Set<JobKey> listJobs(Scheduler scheduler) throws SchedulerException {
        return scheduler.getJobKeys(GroupMatcher.anyGroup());
    }

    /**
     * 获取系统中已添加的指定的任务
     *
     * @param scheduler    调度器
     * @param jobClassName 任务类名
     * @return JobKey
     */
    public static JobKey getJobByGroupAndName(Scheduler scheduler, String jobClassName) throws SchedulerException {
        return getJobByGroupAndName(scheduler, JOB_NAME_PRE + jobClassName, JOB_GROUP_PRE + jobClassName);
    }


    /**
     * 获取系统中已添加的指定的任务
     *
     * @param scheduler    调度器
     * @param jobName      任务名名
     * @param jobGroupName 任务组名
     * @return JobKey
     */
    public static JobKey getJobByGroupAndName(Scheduler scheduler, String jobName, String jobGroupName) throws SchedulerException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyGroup());
        for (JobKey jobKey : jobKeys) {
            String key = jobKey.getGroup() + "." + jobKey.getName();
            if (key.equals(jobGroupName + "." + jobName)) {
                return jobKey;
            }
        }
        return null;
    }

}
