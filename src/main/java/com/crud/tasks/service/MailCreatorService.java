package com.crud.tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Service
public class MailCreatorService {
    @Autowired
    private SpringTemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://martaglad.github.io/");
        context.setVariable("button", "Visit website");

        return templateEngine.process("mail/created-trello-card-mail", context);

    }
}
