//package com.sfa22.ScaleTickets.smssender;
//
//import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
//import com.sfa22.ScaleTickets.dtos.InputTicketDto;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.rest.api.v2010.account.MessageCreator;
//import com.twilio.type.PhoneNumber;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//@Service("twilio")
//public class TwilioSmsSender implements SmsSender {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);
//
//    private final TwilioConfig twilioConfig;
//
//    public TwilioSmsSender(TwilioConfig twilioConfig) {
//        this.twilioConfig = twilioConfig;
//    }
//
//    @Override
//    public void sendSms(InputTicketDto inputTicketDto, DisplayTicketDto displayTicketDto)  {
//
//        if (isPhoneNumberValid(inputTicketDto.getPhoneNumber())) {
//
//            PhoneNumber to = new PhoneNumber(inputTicketDto.getPhoneNumber());
//            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
//
//            String message = inputTicketDto.getClientName() + " thank you for purchasing a ticket from ScaleTickets! " +
//                    "you will depart from " + displayTicketDto.getDepartureCity()  + " on " + displayTicketDto.getDepartureDate() +
//                    " your seat number is: " + displayTicketDto.getSeatNumber();
//            MessageCreator creator = Message.creator(to, from, message);
//            creator.create();
//            LOGGER.info("Send sms {}", inputTicketDto);
//        } else {
//            throw new IllegalArgumentException(
//                    "Phone number [" + inputTicketDto.getPhoneNumber() + "] is not a valid number"
//            );
//        }
//
//    }
//
//    private boolean isPhoneNumberValid(String phoneNumber) {
//        return phoneNumber.contains("+3598") && phoneNumber.length() == 13;
//    }
//
//}