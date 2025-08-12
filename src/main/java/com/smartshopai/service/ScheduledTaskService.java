package com.smartshopai.service;

public interface ScheduledTaskService {

    void sendPaymentReminders();

    void processFailedNotifications();

    void cleanupExpiredFiles();

    void generateDailyPaymentReports();

    void sendWeeklyAnnouncements();
}
