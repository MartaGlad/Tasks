package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://martaglad.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview", "Trello Card added");
        context.setVariable("goodbye","Have a nice day!");
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_email", companyConfig.getCompanyEmail());
        context.setVariable("company_phone", companyConfig.getCompanyPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);

        return templateEngine.process("mail/created-trello-card-mail", context);

    }

    public String buildTaskNumberEmail(String message) {
        List<String> encouragements = new ArrayList<>();
        encouragements.add("Every progress counts");
        encouragements.add("Small steps lead to big results");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://martaglad.github.io/");
        context.setVariable("button", "Visit website to see your tasks");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview", "Current number of tasks");
        context.setVariable("goodbye", "Good luck with your tasks!");
        context.setVariable("company_details", companyConfig.getCompanyDetails());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("encouragements", encouragements);

        return templateEngine.process("mail/task-number-reminder-mail", context);
    }
}
