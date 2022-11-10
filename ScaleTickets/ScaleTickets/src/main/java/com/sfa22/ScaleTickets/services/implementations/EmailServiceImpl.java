package com.sfa22.ScaleTickets.services.implementations;

import org.thymeleaf.ITemplateEngine;
import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.services.interfaces.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;
    private ITemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Method that notifies the user of their ticket purchase with the relevant details
     * about the booked trip.
     * For the purpose of styling the email with html and css design, a thymeleaf template
     * engine is being used for styling the input data with the /email.html
     * located in the resources/templates folder.

     * @param displayTicketDto
     * @param email
     * @throws MessagingException in case of issues with the email sending
     */

    @Override
    public void sendEmail(DisplayTicketDto displayTicketDto, String email) throws MessagingException {

        Context context = new Context();
        context.setVariable("ticket", displayTicketDto);

        String process = templateEngine.process("/email", context);


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Hello " + displayTicketDto.getClientName());
        helper.setText(process, true);
        javaMailSender.send(message);

    }
}
