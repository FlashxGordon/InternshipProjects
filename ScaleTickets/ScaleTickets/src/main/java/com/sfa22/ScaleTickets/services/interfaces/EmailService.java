package com.sfa22.ScaleTickets.services.interfaces;

import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmail(DisplayTicketDto displayTicketDto,String email) throws MessagingException;
}
