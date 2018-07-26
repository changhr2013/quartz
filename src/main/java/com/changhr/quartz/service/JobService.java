package com.changhr.quartz.service;

import com.changhr.quartz.dao.JobDao;
import com.changhr.quartz.domain.ScheduleJob;
import com.changhr.quartz.exception.ServiceException;
import com.changhr.quartz.util.ScheduleUtil;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 定时任务Service
 *
 * @author changhr
 * @create 2018-07-25 19:45
 */
@Service
public class JobService {
    @Autowired
    private JobDao jobDao;

    @Autowired
    private Scheduler scheduler;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<ScheduleJob> getAllEnableJob() {
        return jobDao.getAllEnableJob();
    }

    public ScheduleJob select(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = jobDao.select(jobId);
        if (scheduleJob == null) {
            throw new ServiceException("ScheduleJob:" + jobId + " not found");
        }
        return scheduleJob;
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public ScheduleJob update(Long jobId, ScheduleJob scheduleJob) throws ServiceException {
        scheduleJob.setId(jobId);
        ScheduleJob oldScheduleJob = jobDao.select(jobId);
        BeanUtils.copyProperties(oldScheduleJob, scheduleJob, getNullPropertyNames(scheduleJob));
        if (jobDao.update(scheduleJob) <= 0) {
            throw new ServiceException("Update product:" + jobId + "failed");
        }

        ScheduleUtil.updateScheduleJob(scheduler, scheduleJob);

        return scheduleJob;
    }


    /**
     * @Title: getNullPropertyNames
     * @Description: 获取值为空的属性名称
     * @createdBy:byrc
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean add(ScheduleJob scheduleJob) throws ServiceException {
        Integer num = jobDao.insert(scheduleJob);
        if (num <= 0) {
            throw new ServiceException("Add product failed");
        }

        ScheduleUtil.createScheduleJob(scheduler, scheduleJob);

        return true;
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean delete(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = select(jobId);

        Integer num = jobDao.delete(jobId);
        if (num <= 0) {
            throw new ServiceException("Delete product:" + jobId + "failed");
        }

        ScheduleUtil.deleteJob(scheduler, scheduleJob);

        return true;
    }

    public List<ScheduleJob> getAllJob() {
        return jobDao.getAllJob();
    }

    public boolean resume(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = updateScheduleJobStatus(jobId, false);
        ScheduleUtil.resumeJob(scheduler, scheduleJob);
        return true;
    }

    public boolean pause(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = updateScheduleJobStatus(jobId, true);
        ScheduleUtil.pauseJob(scheduler, scheduleJob);
        return true;
    }

    public boolean run(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = updateScheduleJobStatus(jobId, false);
        ScheduleUtil.run(scheduler, scheduleJob);
        return true;
    }


    private ScheduleJob updateScheduleJobStatus(Long jobId, Boolean isPause) throws ServiceException {
        ScheduleJob scheduleJob = select(jobId);
        scheduleJob.setPause(isPause);
        update(scheduleJob.getId(), scheduleJob);
        return scheduleJob;
    }
}
