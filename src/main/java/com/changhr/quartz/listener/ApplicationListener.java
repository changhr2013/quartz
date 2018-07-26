package com.changhr.quartz.listener;

import com.changhr.quartz.domain.ScheduleJob;
import com.changhr.quartz.exception.ServiceException;
import com.changhr.quartz.service.JobService;
import com.changhr.quartz.util.MyScheduleUtil;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

/**
 * Listener
 *
 * @author changhr
 * @create 2018-07-26 11:35
 */
public class ApplicationListener implements CommandLineRunner{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JobService jobService;

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        //应用启动之后执行所有可执行的任务
        List<ScheduleJob> scheduleJobList = jobService.getAllEnableJob();
        for(ScheduleJob scheduleJob : scheduleJobList){
            try{
                CronTrigger cronTrigger = MyScheduleUtil.getCronTrigger(scheduler, scheduleJob);
                if(cronTrigger == null){
                    MyScheduleUtil.createScheduleJob(scheduler, scheduleJob);
                }else {
                    MyScheduleUtil.updateScheduleJob(scheduler, scheduleJob);
                }
                logger.info("Startup {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
            }catch (ServiceException e){
                e.printStackTrace();
            }
        }
    }
}
