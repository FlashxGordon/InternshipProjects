package com.sfa22.ScaleTickets.smssender;

import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.dtos.InputTicketDto;

public interface SmsSender {

    void sendSms(InputTicketDto inputTicketDto, DisplayTicketDto displayTicketDto);

}