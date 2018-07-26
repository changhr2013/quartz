package com.changhr.quartz.job;

import com.changhr.quartz.service.JobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用于封装要定时执行的任务
 *
 * @author changhr
 * @create 2018-07-26 11:57
 */
public class TestJob implements Job{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 如果没有自定义改写 JobFactory，这里会注入失败
    @Autowired
    private JobService jobService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info(">>>> The Test Job is Executing...");
    }
}
