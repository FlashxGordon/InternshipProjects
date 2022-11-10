package com.sfa22.ScaleTickets.smssender;

import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.dtos.InputTicketDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final SmsSender smsSender;

    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(InputTicketDto inputTicketDto,
                        DisplayTicketDto displayTicketDto) {

        smsSender.sendSms(inputTicketDto, displayTicketDto);
    }
}