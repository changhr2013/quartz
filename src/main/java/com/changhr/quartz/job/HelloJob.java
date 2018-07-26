package com.changhr.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

/**
 * hello job test
 *
 * @author changhr
 * @create 2018-07-26 16:30
 */
public class HelloJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("##I'm HelloJob -- " + LocalTime.now());
    }
}
