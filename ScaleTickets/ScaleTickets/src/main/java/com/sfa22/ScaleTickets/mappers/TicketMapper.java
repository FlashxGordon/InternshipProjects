package com.sfa22.ScaleTickets.mappers;

import com.sfa22.ScaleTickets.dtos.InputTicketDto;
import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.entities.Discount;
import com.sfa22.ScaleTickets.entities.Ticket;
import com.sfa22.ScaleTickets.entities.Trip;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component

public class TicketMapper {

    public Ticket mapDisplayTicketDtoToTicket(DisplayTicketDto displayTicketDto, String email, String phoneNumber,
                                              Trip trip, Discount discount) {
        return new Ticket(displayTicketDto.getTicketId(), displayTicketDto.getClientName().
                split(" ")[0].trim(),
                displayTicketDto.getClientName().
                        split(" ")[1].trim(), email, phoneNumber, displayTicketDto.getTicketPrice(),
                trip, discount, trip.getRemainingSeats());

    }

    public DisplayTicketDto mapTicketToDisplayTicketDto(Ticket ticket) {
        String clientName = ticket.getFirstName() + " " + ticket.getLastName();
        return new DisplayTicketDto(ticket.getTicketId(), clientName, ticket.getTicketPrice(),
                ticket.getTrip().getBus().getBusPlate(), ticket.getTrip().getRoute().getDepartureCity(),
                ticket.getTrip().getRoute().getArrivalCity(), ticket.getTrip().getDepartureDate(), ticket.getSeatNumber());

    }

    public Ticket mapInputTicketDtoToTicket(InputTicketDto inputTicketDto,
                                            double price, Trip trip, Discount discount) {
        return new Ticket(inputTicketDto.getClientName().
                split(" ")[0].trim(),
                inputTicketDto.getClientName().
                        split(" ")[1].trim(), inputTicketDto.getEmail(), inputTicketDto.getPhoneNumber(),
                price, trip, discount, trip.getRemainingSeats());

    }

    public InputTicketDto mapTicketToInputTicketDto(Ticket ticket) {
        String clientName = ticket.getFirstName() + " " + ticket.getLastName();
        return new InputTicketDto(clientName, ticket.getEmail(), ticket.getPhoneNumber(),
                ticket.getTrip().getTripId(), ticket.getDiscount().getCode());

    }

    public List<DisplayTicketDto> ListOfTicketToDisplayTicketDto(List<Ticket> tickets) {

        return tickets.stream().map(this::mapTicketToDisplayTicketDto).collect(Collectors.toList());
    }
}
