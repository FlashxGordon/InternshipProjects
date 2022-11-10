package com.sfa22.ScaleTickets.services;


import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.services.implementations.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {


    private static final String TEMPLATE_USER_CREATION = "/email";
    @Mock
    private ITemplateEngine templateEngine;
    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailServiceImpl emailServiceImpl;

    private MimeMessage mimeMessage;
    private String email;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mimeMessage = new MimeMessage((Session) null);
        email = "angelafisher1996@gmail.com";


    }

    @Test
    public void emailTest() throws MessagingException {
        String userCreationHtml = "<html><h1>Hi</h1></html>";
        when(templateEngine.process(eq(TEMPLATE_USER_CREATION), any(Context.class))).thenReturn(userCreationHtml);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        String recipient = email;
        DisplayTicketDto displayTicketDto = new DisplayTicketDto("Angela Fisher",
                20.0, "AB243", "Skopje", "Ohrid",
                LocalDateTime.now().plusDays(2), 20);
        emailServiceImpl.sendEmail(displayTicketDto, email);
        assertEquals(recipient, mimeMessage.getRecipients(MimeMessage.RecipientType.TO)[0].toString());
    }
}
