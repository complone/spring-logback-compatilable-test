package com.example.logback.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestTask implements Runnable{
    
    private static Logger LOG = LoggerFactory.getLogger(TestTask.class);
    
    private String taskId;
    
    private String userId;
    
    public TestTask(String userId, String taskId){
        this.taskId = taskId;
        this.userId = userId;
    }
    
    public void run() {
        try{
            
            // 上面logback.xml中discriminator根据taskId这个key的value来决定，taskId的value通过这种方式设置，
            // 这里设置的key-value对是保存在一个ThreadLocal<Map>中的，所以不会对其他线程中的taskId这个key产生影响
            TaskContextHolder.getContext().setTaskId(taskId);
            TaskContextHolder.getContext().setUserId(userId);
            throw new IllegalArgumentException("非法参数");
            
        }catch (Exception e){
            LOG.error("taskId= {}, userId={}, 当前报错无显示 ", taskId, userId);
        }finally {
           TaskContextHolder.clear();
        }
    }
    
    public static void main(String[] args){
        ExecutorService taskExecutors = Executors.newFixedThreadPool(10);
        for (int i =0;i< 10; ++i){
            taskExecutors.submit(new TestTask("task"+i, "user"+i));
        }
    }
}
