package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleEmailService {

    private final JavaMailSender javaMailSender; //interfejs

    @Autowired
    private MailCreatorService mailCreatorService;

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }


    public void send(final Mail mail) throws MailException {
        log.info("Starting e-mail preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail));
            log.info("E-mail has been sent.");
        } catch (MailException e) {
            log.error("Failed to process e-mail sending: " + e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessageForNumberOfTasks(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTaskNumberEmail(mail.getMessage()), true);
        };
    }

    public void sendNumberOfTasksDaily(final Mail mail) throws MailException {
        log.info("Starting e-mail preparation...");
        try {
            javaMailSender.send(createMimeMessageForNumberOfTasks(mail));
            log.info("E-mail has been sent.");
        } catch (MailException e) {
            log.error("Failed to process e-mail sending: " + e.getMessage(), e);
        }
    }



    /*public void send(final Mail mail) throws MailException {
        log.info("Starting e-mail preparation...");
        try {
            SimpleMailMessage mailMessage = createMailMessage(mail);
            javaMailSender.send(mailMessage);
            log.info("E-mail has been sent.");
        } catch (MailException e) {
            log.error("Failed to process e-mail sending: " + e.getMessage(), e);
        }
    }*/

   /* private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        Optional<String> ccOptional = Optional.ofNullable(mail.getToCc());
        if(ccOptional.isPresent() && !ccOptional.get().isEmpty()) {
            mailMessage.setCc(ccOptional.get());
        }
        return mailMessage;
    }*/
}
