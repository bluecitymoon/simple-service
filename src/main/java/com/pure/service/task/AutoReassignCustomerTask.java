package com.pure.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AutoReassignCustomerTask {

    private final Logger log = LoggerFactory.getLogger(AutoReassignCustomerTask.class);
    /**
     * 每月底把上月未完成的新单重新分配到下月份继续完成
     * 并将上月的报告存档.
     * 每月的第一天的 00：01: 01启动这个任务
     */
    @Scheduled(cron = "01 01 00 1 * ?")
//    @Scheduled(cron = "01 59 20 8 * ?")
    public void reassign() {

        LocalDateTime now = LocalDateTime.now();

        log.info("开始启动自动重分配任务");
    }

//    @Scheduled(fixedRate = 2000)
//    public void testLog() {
//
//        log.info("" + System.currentTimeMillis());
//    }
}
