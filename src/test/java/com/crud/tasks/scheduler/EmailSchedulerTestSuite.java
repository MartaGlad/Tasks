package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSchedulerTestSuite {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService simpleEmailService; //ArgumentCaptor przechwytuje argument wywołań metody send tego mocka

    @Test
    void testSendInformationEmail() {
        //Given
        when(taskRepository.count()).thenReturn(1L);
        when(adminConfig.getAdminMail()).thenReturn("admin7855@gmail.com");
        //When
        emailScheduler.sendInformationEmail();
        //Then
        ArgumentCaptor<Mail> mCaptor = ArgumentCaptor.forClass(Mail.class);
        verify((simpleEmailService), times(1)).sendNumberOfTasksDaily(mCaptor.capture());
        //sprawdza, czy send() zostało wywołane i zapamiętuje argument, który został przekazany
        Mail capturedMail = mCaptor.getValue();
        assertNotNull(capturedMail);
        assertEquals("admin7855@gmail.com", capturedMail.getMailTo());
        assertEquals("Tasks: Once a day email", capturedMail.getSubject());
        assertEquals("Currently in database you got: 1 task.", capturedMail.getMessage());
        assertNull(capturedMail.getToCc());
    }

    @Test
    void testSendInformationEmailWithPluralWord() {
        //Given
        when(taskRepository.count()).thenReturn(2L);
        when(adminConfig.getAdminMail()).thenReturn("admin7855@gmail.com");
        //When
        emailScheduler.sendInformationEmail();
        //Then
        ArgumentCaptor<Mail> mCaptor = ArgumentCaptor.forClass(Mail.class);
        verify((simpleEmailService), times(1)).sendNumberOfTasksDaily(mCaptor.capture());
        Mail capturedMail = mCaptor.getValue();
        assertEquals("Currently in database you got: 2 tasks.", capturedMail.getMessage());
    }
}