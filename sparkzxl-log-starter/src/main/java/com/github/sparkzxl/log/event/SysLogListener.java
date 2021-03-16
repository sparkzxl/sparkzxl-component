package com.github.sparkzxl.log.event;

import com.github.sparkzxl.log.entity.OperateLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.function.Consumer;

/**
 * description: 异步监听日志事件
 *
 * @author zhouxinlei
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

    private final Consumer<OperateLog> consumer;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        OperateLog sysLog = (OperateLog) event.getSource();
        consumer.accept(sysLog);
    }

}
