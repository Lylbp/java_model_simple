package com.lylbp.project.task;

import cn.hutool.core.date.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author weiwenbin
 * @Date: 2020/10/19 下午2:47
 */
public class TestTask implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("注意啦！！！！！==========定时任务启用啦-----"+ DateUtil.now());
    }
}
