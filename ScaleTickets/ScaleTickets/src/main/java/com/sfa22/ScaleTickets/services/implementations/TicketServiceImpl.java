package com.sfa22.ScaleTickets.services.implementations;

import com.sfa22.ScaleTickets.data.DiscountCodeRepository;
import com.sfa22.ScaleTickets.data.TicketRepository;
import com.sfa22.ScaleTickets.data.TripRepository;
import com.sfa22.ScaleTickets.dtos.InputTicketDto;
import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.entities.Discount;
import com.sfa22.ScaleTickets.entities.Ticket;
import com.sfa22.ScaleTickets.entities.Trip;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.NoMoreSeatsException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.exceptions.UnavailableTransportException;
import com.sfa22.ScaleTickets.mappers.TicketMapper;
import com.sfa22.ScaleTickets.services.interfaces.EmailService;
import com.sfa22.ScaleTickets.services.interfaces.TicketService;
import com.sfa22.ScaleTickets.smssender.TwilioSmsSender;
import com.sfa22.ScaleTickets.validations.DiscountValidation;
import com.sfa22.ScaleTickets.validations.UserTicketValidation;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketMapper mapper;
    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    private final DiscountCodeRepository discountCodeRepository;
    private final EmailService emailService;
    private final TwilioSmsSender twilioSmsSender;

    private final UserTicketValidation validation;
    private final DiscountValidation validator;

    public TicketServiceImpl(TicketMapper mapper, TicketRepository ticketRepository,
                             TripRepository tripRepository,
                             DiscountCodeRepository discountCodeRepository, EmailService emailService, TwilioSmsSender twilioSmsSender, UserTicketValidation validation, DiscountValidation validator) {
        this.mapper = mapper;
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
        this.discountCodeRepository = discountCodeRepository;
        this.emailService = emailService;
        this.twilioSmsSender = twilioSmsSender;
        this.validation = validation;
        this.validator = validator;
    }

    @Override
    public List<DisplayTicketDto> getAllTickets() {

        List<Ticket> tickets = ticketRepository.findAll();

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public Optional<DisplayTicketDto> getById(int id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceMissingException("Ticket not found", "Ticket"));

        return Optional.ofNullable(mapper.mapTicketToDisplayTicketDto(ticket));

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByFirstNameAndLastName(String firstName, String lastName) {

        List<Ticket> tickets = ticketRepository.
                findTicketsByFirstNameAndLastName(firstName, lastName);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByFirstName(String firstName) {

        List<Ticket> tickets = ticketRepository.findTicketsByFirstName(firstName);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByLastName(String lastName) {

        List<Ticket> tickets = ticketRepository.findTicketsByLastName(lastName);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByPrice(double price) {

        List<Ticket> tickets = ticketRepository.findTicketsByTicketPrice(price);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByDiscountCode(String code) {

        List<Ticket> tickets = ticketRepository.findAllByDiscount_Code(code);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByDepartureCity(String departureCity) {

        List<Ticket> tickets = ticketRepository.findAllByTrip_Route_DepartureCity(departureCity);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByArrivalCity(String arrivalCity) {

        List<Ticket> tickets = ticketRepository.findAllByTrip_Route_ArrivalCity(arrivalCity);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByArrivalDateTime(LocalDateTime arrivalDateTime) {

        List<Ticket> tickets = ticketRepository.findAllByTrip_ArrivalDate(arrivalDateTime);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByDepartureDateTime(LocalDateTime departureDateTime) {

        List<Ticket> tickets = ticketRepository.findAllByTrip_DepartureDate(departureDateTime);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByDepartureDateLessThan(LocalDateTime dateTime) {

        List<Ticket> tickets = ticketRepository.findByDepartureDateLessThan(dateTime);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByDepartureDateGreaterThanEqual(LocalDateTime dateTime) {

        List<Ticket> tickets = ticketRepository.findByDepartureDateGreaterThanEqual(dateTime);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByArrivalLessThan(LocalDateTime dateTime) {

        List<Ticket> tickets = ticketRepository.findByArrivalLessThan(dateTime);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    @Override
    public List<DisplayTicketDto> getAllTicketsByArrivalDateGreaterThanEqual(LocalDateTime dateTime) {

        List<Ticket> tickets = ticketRepository.findByArrivalDateGreaterThanEqual(dateTime);

        return mapper.ListOfTicketToDisplayTicketDto(tickets);

    }

    /**
     * Method processes input data for the purpose of creating a new ticket for selected trip.
     * Calculation of discounted price of the ticket in the case of provided discount code is provided.
     * In the case of provided expired discount code it evaluates the price by the regular cost.
     *
     * Depending on user input it notifies the user of his purchase through email or SMS.
     * It updates the number of the seats of the booked trip, after each purchase of a ticket.
     *
     * @param inputTicketDto which represents the user input for purchasing a ticket
     * @return the id of the newly created ticket in database
     * @throws MessagingException in case of issues with the email sending feature
     */

    @Override
    public int saveTicket(InputTicketDto inputTicketDto) throws MessagingException {
        double price = 0.0;
        Ticket ticket = null;

        if (!validation.isUserNameValid(inputTicketDto.getClientName())) {
            throw new InvalidUserInputException("Incorrect name format." +
                    " Please insert your name correctly" +
                    "  ex. Robert Smith");
        }


        Trip trip = tripRepository.findById(inputTicketDto.getTripId())
                .orElseThrow(() -> new ResourceMissingException("Trip not found", "Trip"));


        if (trip.getDepartureDate().isBefore(LocalDateTime.now())) {
            throw new UnavailableTransportException("This trip has already expired.");
        }

        if (trip.getRemainingSeats() == 0) {
            throw new NoMoreSeatsException("All seats for this trip have been booked");
        } else {


            if (inputTicketDto.getDiscountCode().isEmpty()) {

                ticket = createTicketWithNoDiscount(inputTicketDto, trip);

            } else {

                if (!validator.isValid(inputTicketDto.getDiscountCode())) {
                    throw new InvalidUserInputException("Discount code format is incorrect. Try again.");
                } else {

                    ticket = createTicketWithDiscount(inputTicketDto, trip);

                }

            }

            if (inputTicketDto.getEmail().contains("@scalemail.com")) {

                addBootstrapedTickets(ticket, trip);

            } else {

                addIncomingTicket(inputTicketDto, ticket, trip);


            }
        }
        return ticket.getTicketId();
    }

    /**
     * Method that saves the new incoming ticket provided by the user.
     * @param inputTicketDto
     * @param ticket
     * @param trip
     * @throws MessagingException
     */

    private void addIncomingTicket(InputTicketDto inputTicketDto, Ticket ticket, Trip trip) throws MessagingException {
        DisplayTicketDto displayTicketDto = mapper.mapTicketToDisplayTicketDto(ticket);

        handleSmsAndEmailNotifications(inputTicketDto, ticket, displayTicketDto);

        ticketRepository.save(ticket);

        updateSeats(trip);
    }

    /**
     * Method that saves the bootstrapped tickets to the database
     * @param ticket
     * @param trip
     */

    private void addBootstrapedTickets(Ticket ticket, Trip trip) {
        ticketRepository.save(ticket);

        updateSeats(trip);
    }

    /**
     * Method that handles the email and sms sending functionalities, depending on
     * what way the user has inserted that he would like to be notified of his purchase,
     * a different action is triggered.
     * @param inputTicketDto
     * @param ticket
     * @param displayTicketDto
     * @throws MessagingException in the case of issues with the email functionalities
     */

    private void handleSmsAndEmailNotifications(InputTicketDto inputTicketDto, Ticket ticket, DisplayTicketDto displayTicketDto) throws MessagingException {
        if (inputTicketDto.getPhoneNumber().isEmpty() || inputTicketDto.getPhoneNumber().equals("")) {


            emailService.sendEmail(displayTicketDto, ticket.getEmail());


        }
        if (inputTicketDto.getEmail().isEmpty() || inputTicketDto.getEmail().equals(" ")) {
            if (!validation.isUserPhoneNumberValid(inputTicketDto.getPhoneNumber())) {
                throw new InvalidUserInputException("Invalid phone number format." +
                        " Please insert your phone number correctly");
            }


            twilioSmsSender.sendSms(inputTicketDto, displayTicketDto);


        }
        if (!inputTicketDto.getEmail().isEmpty() && !inputTicketDto.getEmail().isBlank() &&
                !inputTicketDto.getPhoneNumber().isEmpty() &&
                !inputTicketDto.getPhoneNumber().isBlank()) {

            if (!validation.isUserPhoneNumberValid(inputTicketDto.getPhoneNumber())) {
                throw new InvalidUserInputException("Invalid phone number format." +
                        " Please insert your phone number correctly");
            }

            emailService.sendEmail(displayTicketDto, ticket.getEmail());

            twilioSmsSender.sendSms(inputTicketDto, displayTicketDto);


        }
    }

    /**
     * Method that calculates the price of the newly created ticket by the provided
     * discount coupon from the user input data.
     * @param inputTicketDto
     * @param trip
     * @return ticket - the newly created ticket
     */

    private Ticket createTicketWithDiscount(InputTicketDto inputTicketDto, Trip trip) {
        double price;
        Ticket ticket;
        Discount discount = discountCodeRepository.findByCode(inputTicketDto.getDiscountCode()).get();

        if (discount.getExpirationDate().isBefore(LocalDate.now())) {

            price = trip.getRoute().getTripPrice();

        } else {
            price = getDiscountedPrice(trip, discount);
        }

        ticket = mapper.mapInputTicketDtoToTicket(inputTicketDto, price, trip, discount);
        return ticket;
    }

    /**
     * Method that calculates the price of the ticket that is being created by its regular price.
     * @param inputTicketDto
     * @param trip
     * @return ticket - the newly created ticket
     */

    private Ticket createTicketWithNoDiscount(InputTicketDto inputTicketDto, Trip trip) {
        double price;
        Ticket ticket;
        price = trip.getRoute().getTripPrice();

        ticket = mapper.mapInputTicketDtoToTicket(inputTicketDto, price, trip, null);
        return ticket;
    }

    /**
     * Method that calculates discounted price by provided discount code percentage
     * from the ticket input data.
     * @param trip
     * @param discount
     * @return price
     */

    private static double getDiscountedPrice(Trip trip, Discount discount) {

        double discountedAmount = trip.getRoute().getTripPrice() * discount.getPercentage() / 100;

        double price = trip.getRoute().getTripPrice() - discountedAmount;

        return price;

    }

    /**
     * Method that updates the number of seats in the booked trip after a new purchase
     * of a ticket by decrementing the number of seats.
     * @param trip
     */

    private void updateSeats(Trip trip) {

        int seats = trip.getRemainingSeats() - 1;

        trip.setRemainingSeats(seats);

        tripRepository.save(trip);

    }


}
