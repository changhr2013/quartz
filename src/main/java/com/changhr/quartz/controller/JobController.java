package com.changhr.quartz.controller;

import com.changhr.quartz.domain.ScheduleJob;
import com.changhr.quartz.exception.ServiceException;
import com.changhr.quartz.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Job web接口
 *
 * @author changhr
 * @create 2018-07-26 10:58
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public Object getAllJob(){
        return jobService.getAllJob();
    }

    @GetMapping("/{id}")
    public Object getJob(@PathVariable("id") Long jobId) throws ServiceException {
        return jobService.select(jobId);
    }

    @PutMapping("/update/{id}")
    public Object updateJob(@PathVariable("id") Long jobId,
                            @RequestBody ScheduleJob newScheduleJob) throws ServiceException{
        return jobService.update(jobId, newScheduleJob);
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteJob(@PathVariable("id") Long jobId) throws ServiceException {
        return jobService.delete(jobId);
    }

    @PostMapping("/save")
    public Object saveJob(@RequestBody ScheduleJob newScheduleJob) throws ServiceException{
        return jobService.add(newScheduleJob);
    }

    @GetMapping("/run/{id}")
    public Object runJob(@PathVariable("id") Long jobId) throws ServiceException {
        return jobService.run(jobId);
    }

    @GetMapping("/pause/{id}")
    public Object pauseJob(@PathVariable("id") Long jobId) throws ServiceException {
        System.out.println("--------------------->" + jobId);
        return jobService.pause(jobId);
    }

    @GetMapping("/resume/{id}")
    public Object resumeJob(@PathVariable("id") Long jobId) throws ServiceException {
        return jobService.resume(jobId);
    }

}
