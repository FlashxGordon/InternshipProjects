package com.sfa22.ScaleTickets.services.interfaces;

import com.sfa22.ScaleTickets.dtos.InputTicketDto;
import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;


import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketService {

    List<DisplayTicketDto> getAllTickets();

    Optional<DisplayTicketDto> getById(int id);

    List<DisplayTicketDto> getAllTicketsByFirstNameAndLastName(String firstName, String lastName);

    List<DisplayTicketDto> getAllTicketsByFirstName(String firstName);

    List<DisplayTicketDto> getAllTicketsByLastName(String lastName);

    List<DisplayTicketDto> getAllTicketsByPrice(double price);

    List<DisplayTicketDto> getAllTicketsByDiscountCode(String code);

    List<DisplayTicketDto> getAllTicketsByDepartureCity(String departureCity);

    List<DisplayTicketDto> getAllTicketsByArrivalCity(String arrivalCity);

    List<DisplayTicketDto> getAllTicketsByArrivalDateTime(LocalDateTime arrivalDateTime);

    List<DisplayTicketDto> getAllTicketsByDepartureDateTime(LocalDateTime departureDateTime);

    List<DisplayTicketDto> getAllTicketsByDepartureDateLessThan(LocalDateTime dateTime);

    List<DisplayTicketDto> getAllTicketsByDepartureDateGreaterThanEqual(LocalDateTime dateTime);

    List<DisplayTicketDto> getAllTicketsByArrivalLessThan(LocalDateTime dateTime);

    List<DisplayTicketDto> getAllTicketsByArrivalDateGreaterThanEqual(LocalDateTime dateTime);

    int saveTicket(InputTicketDto ticketDto) throws MessagingException;

}
