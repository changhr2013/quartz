package com.changhr.quartz.config;

import com.changhr.quartz.factory.ScheduleJobFactory;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * Bean配置
 *
 * @author m4000e
 * @create 2018-07-26 15:31
 */
@Configuration
public class ScheduleConfig {

    @Autowired
    private ScheduleJobFactory scheduleJobFactory;

    @Bean
    @Qualifier("scheduleBean")
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("dataSource") DataSource dataSource) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setSchedulerName("TASK_EXECUTOR");
        // 延迟10秒启动
        schedulerFactoryBean.setStartupDelay(10);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setDataSource(dataSource);
        // 将 JobFactory 改为自定义的，否则在 Job 中注入 Bean 会失败
        schedulerFactoryBean.setJobFactory(scheduleJobFactory);
        return schedulerFactoryBean;
    }
}
