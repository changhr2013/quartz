package com.changhr.quartz.dao;

import com.changhr.quartz.domain.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by m4000e on 2018/7/25.
 */
@Mapper
public interface JobDao {
    ScheduleJob select(@Param("id") long id);

    Integer update(ScheduleJob scheduleJob);

    Integer insert(ScheduleJob scheduleJob);

    Integer delete(Long jobId);

    List<ScheduleJob> getAllJob();

    List<ScheduleJob> getAllEnableJob();
}
