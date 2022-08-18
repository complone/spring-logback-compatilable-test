package com.example.logback.task;

import com.alibaba.ttl.TransmittableThreadLocal;

public class TaskContextHolder {
    
    private static final TransmittableThreadLocal<TaskContext> taskContextStore = new TransmittableThreadLocal<TaskContext>();
    
    /**
     * 获取上下文
     *
     * @return
     */
    public static TaskContext getContext() {
        TaskContext context = taskContextStore.get();
        // 没有保存上下文,则返回对象
        if (context == null) {
            taskContextStore.set(new TaskContext());
            return taskContextStore.get();
        }
        return context;
    }
    
    /**
     * 设置上下文
     *
     * @param context
     */
    public static void setContext(TaskContext context) {
        taskContextStore.set(context);
    }
    
    /**
     * 清空
     */
    public static void clear() {
        taskContextStore.remove();
    }
}
