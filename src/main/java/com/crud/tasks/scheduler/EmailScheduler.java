package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private final MailCreatorService mailCreatorService;
    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: Once a day email";

    @Scheduled(cron = "0 0 10 * * *")
    //@Scheduled(fixedRate = 10000)
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String message = "Currently in database you got: " + size + (size == 1 ? " task." : " tasks.");
        simpleEmailService.sendNumberOfTasksDaily(
                new Mail(
                        adminConfig.getAdminMail(),
                        SUBJECT,
                        message,
                        null
                )
        );
    }
}
