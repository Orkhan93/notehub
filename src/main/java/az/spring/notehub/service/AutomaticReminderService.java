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

    @Scheduled(cron = "0 20 9 * * *") // her gun saat 09:20`de ise dusecek...
    public void automaticReminderCheck() {
        log.info("Inside automaticReminderCheck : ");
        reminderService.checkAndSendReminders();
    }

}