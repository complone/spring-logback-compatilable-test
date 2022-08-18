package com.example.logback.discriminator;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.sift.AbstractDiscriminator;
import com.example.logback.task.TaskContextHolder;
import org.springframework.util.StringUtils;

public class TaskContextBasedDiscriminator extends AbstractDiscriminator<ILoggingEvent> {
    
    private static String LOG_FILE_KEY = "taskId";
    private static String DEFAULT_VALUE = "unknown";
    
    @Override
    public String getKey() {
        return LOG_FILE_KEY;
    }
    
    @Override
    public String getDiscriminatingValue(ILoggingEvent iLoggingEvent) {
        // 这里是从上下文中获取key的值。而MDCBasedDiscriminator从MDC中获取key。
        String taskId = TaskContextHolder.getContext().getTaskId();
        String userId = TaskContextHolder.getContext().getUserId();
        
        //如果当前dlink未接入权限系统，先按照taskID切分
        if (!StringUtils.isEmpty(taskId)) {
            if (!StringUtils.isEmpty(userId)) {
                return userId + "-" + taskId;
            } else {
                return taskId;
            }
        } else {
            return DEFAULT_VALUE;
        }
    }
}
