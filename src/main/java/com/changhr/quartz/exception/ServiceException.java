package com.changhr.quartz.exception;

/**
 * 服务异常处理
 *
 * @author changhr
 * @create 2018-07-25 18:49
 */
public class ServiceException extends Exception {

    public ServiceException(String msg, Exception e){
        super(msg + "\n" + e.getMessage());
    }

    public ServiceException(String msg){
        super(msg);
    }
}
