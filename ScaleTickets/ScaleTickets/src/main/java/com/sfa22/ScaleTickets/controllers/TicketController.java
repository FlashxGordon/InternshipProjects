package com.sfa22.ScaleTickets.controllers;

import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.dtos.InputTicketDto;
import com.sfa22.ScaleTickets.services.interfaces.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<DisplayTicketDto>> getAllTickets() {

        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<DisplayTicketDto> getTicketById(@PathVariable int id) {

        return ResponseEntity.ok(ticketService.getById(id).get());
    }

    @GetMapping(value = "/tickets", params = {"firstName", "lastName"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByFirstNameAndLastName(
            @RequestParam String firstName, @RequestParam String lastName) {

        return ResponseEntity.ok(ticketService.getAllTicketsByFirstNameAndLastName(firstName, lastName));
    }

    @GetMapping(value = "/tickets", params = {"firstName"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByFirstName(
            @RequestParam String firstName) {

        return ResponseEntity.ok(ticketService.getAllTicketsByFirstName(firstName));
    }

    @GetMapping(value = "/tickets", params = {"lastName"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByLastName(
            @RequestParam String lastName) {

        return ResponseEntity.ok(ticketService.getAllTicketsByLastName(lastName));
    }

    @GetMapping(value = "/tickets", params = {"price"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByPrice(
            @RequestParam double price) {

        return ResponseEntity.ok(ticketService.getAllTicketsByPrice(price));
    }

    @GetMapping(value = "/tickets", params = {"code"})
    public ResponseEntity<List<DisplayTicketDto>> getAllByDiscountCode(
            @RequestParam String code) {

        return ResponseEntity.ok(ticketService.getAllTicketsByDiscountCode(code));
    }

    @GetMapping(value = "/tickets", params = {"departureCity"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByDepartureCity(
            @RequestParam String departureCity) {

        return ResponseEntity.ok(ticketService
                .getAllTicketsByDepartureCity(departureCity));
    }

    @GetMapping(value = "/tickets", params = {"arrivalCity"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByArrivalCity(
            @RequestParam String arrivalCity) {

        return ResponseEntity.ok(ticketService
                .getAllTicketsByArrivalCity(arrivalCity));
    }

    @GetMapping(value = "/tickets", params = {"arrival"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByArrivalDateTime(
            @RequestParam String arrival) {

        return ResponseEntity.ok(ticketService
                .getAllTicketsByArrivalDateTime(LocalDateTime.parse(arrival)));
    }

    @GetMapping(value = "/tickets", params = {"departure"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByDepartureDateTime(
            @RequestParam String departure) {

        return ResponseEntity.ok(ticketService
                .getAllTicketsByDepartureDateTime(LocalDateTime.parse(departure)));
    }

    @GetMapping(value = "/tickets/beforeDepartureDateTime", params = {"date"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByDepartureDateTimeLessThan(
            @RequestParam String date) {

        return ResponseEntity.ok(ticketService
                .getAllTicketsByDepartureDateLessThan(LocalDateTime.parse(date)));
    }


    @GetMapping(value = "/tickets/afterOnDepartureDateTime", params = {"date"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByDepartureDateGreaterThanEqual(
            @RequestParam String date) {

        return ResponseEntity.ok(ticketService
                .getAllTicketsByDepartureDateGreaterThanEqual(LocalDateTime
                        .parse(date)));
    }


    @GetMapping(value = "/tickets/beforeArrivalDate", params = {"date"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByArrivalDateLessThan(
            @RequestParam String date) {

        return ResponseEntity.ok(ticketService
                .getAllTicketsByArrivalLessThan(LocalDateTime.parse(date)));
    }


    @GetMapping(value = "/tickets/afterOnArrivalDate", params = {"date"})
    public ResponseEntity<List<DisplayTicketDto>> getAllTicketsByArrivalDateGreaterThanEqual(
            @RequestParam String date) {

        return ResponseEntity.ok(ticketService
                .getAllTicketsByArrivalDateGreaterThanEqual(LocalDateTime
                        .parse(date)));
    }

    @PostMapping("/tickets")
    public ResponseEntity<Void> saveTicket(@Valid @RequestBody InputTicketDto inputTicketDto) throws MessagingException {

        int id = ticketService.saveTicket(inputTicketDto);

        return ResponseEntity.created(URI.create("/tickets/" + id)).build();
    }


}