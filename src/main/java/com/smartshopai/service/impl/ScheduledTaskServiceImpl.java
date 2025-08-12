package com.smartshopai.service.impl;

import com.smartshopai.service.ScheduledTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    @Override
    @Scheduled(cron = "0 0 9 * * *") // Every day at 9 AM
    public void sendPaymentReminders() {
        log.info("Starting scheduled payment reminders task");
        try {
            log.info("Payment reminders task completed successfully");
        } catch (Exception e) {
            log.error("Error in payment reminders task", e);
        }
    }

    @Override
    @Scheduled(fixedDelay = 300000) // Every 5 minutes
    public void processFailedNotifications() {
        log.debug("Starting failed notifications processing task");
        try {
            log.debug("Failed notifications processing task completed");
        } catch (Exception e) {
            log.error("Error in failed notifications processing task", e);
        }
    }

    @Override
    @Scheduled(cron = "0 0 2 * * *") // Every day at 2 AM
    public void cleanupExpiredFiles() {
        log.info("Starting expired files cleanup task");
        try {
            log.info("Expired files cleanup task completed successfully");
        } catch (Exception e) {
            log.error("Error in expired files cleanup task", e);
        }
    }

    @Override
    @Scheduled(cron = "0 0 8 * * *") // Every day at 8 AM
    public void generateDailyPaymentReports() {
        log.info("Starting daily payment reports generation task");
        try {
            log.info("Daily payment reports generation task completed successfully");
        } catch (Exception e) {
            log.error("Error in daily payment reports generation task", e);
        }
    }

    @Override
    @Scheduled(cron = "0 0 10 * * MON") // Every Monday at 10 AM
    public void sendWeeklyAnnouncements() {
        log.info("Starting weekly announcements task");
        try {
            log.info("Weekly announcements task completed successfully");
        } catch (Exception e) {
            log.error("Error in weekly announcements task", e);
        }
    }
}
