package az.spring.notehub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutomaticReminderService {

    private final ReminderService reminderService;

    @Scheduled(cron = "0 30 9 * * *") // The method will start every day at 09.30...
    public void automaticReminderCheck() {
        log.info("Inside automaticReminderCheck : ");
        reminderService.checkAndSendReminders();
    }

//    @Scheduled(cron = "0 11 16 * * *")
//    public void auto() {
//        log.info("Inside auto : ");
//        reminderService.checkAndSendRemindersXXX();
//    }

}