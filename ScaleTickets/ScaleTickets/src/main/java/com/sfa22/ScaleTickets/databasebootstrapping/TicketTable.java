package com.sfa22.ScaleTickets.databasebootstrapping;

import com.github.javafaker.Faker;
import com.sfa22.ScaleTickets.data.*;
import com.sfa22.ScaleTickets.dtos.InputTicketDto;
import com.sfa22.ScaleTickets.entities.Ticket;
import com.sfa22.ScaleTickets.entities.Trip;
import com.sfa22.ScaleTickets.services.interfaces.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class TicketTable implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(TicketTable.class);
    private final TripRepository tripRepository;
    private final TicketRepository ticketRepository;
    private final Faker faker;
    private final TicketService ticketService;


    public TicketTable(TripRepository tripRepository,
                       TicketRepository ticketRepository,
                       Faker faker,
                       TicketService ticketService) {
        this.tripRepository = tripRepository;
        this.ticketRepository = ticketRepository;
        this.faker = faker;
        this.ticketService = ticketService;
    }


    /**
     * Method which inserts predetermined values into the database
     * the purpose of inserting the information is to  automatically insert data upon server startup so
     * that the application has data that it can manipulate for demonstration purposes.
     * It bootstrapps data in 2 batches of 10 days. The first batch of 10 days only a 1/3 of the seats on a given date
     * are sold out and on the 2 batch about 1/2 of the seats are sold. The purpose of this is to have differentiation
     * in the rate of ticket sales so that the analytics features can be demonstrated.
     * <p>
     * This particular method populates the ticket table with user and travel information
     *
     * @param args
     */


    @Override
    public void run(String... args) throws MessagingException {

        List<Ticket> ticketList = ticketRepository.findAll();

        if (ticketList.isEmpty()) {

            logger.info("Bootstrapping ticket table...");

            List<Trip> tripList = tripRepository.findAll();

            List<Integer> tripIdList = tripList.stream()
                    .map(Trip::getTripId)
                    .collect(Collectors.toList());

            for (int i = 0; i < tripIdList.size(); i++) {

                if (i <= tripList.size() / 2) {

                    int remainingSeats = (tripList.get(i).getRemainingSeats()) / 3;

                    for (int j = 0; j < remainingSeats; j++) {

                        InputTicketDto inputTicketDto =

                                new InputTicketDto(faker.name().firstName() + " " + faker.name().lastName(), getFakeEmail(), faker.phoneNumber()
                                        .cellPhone(), tripIdList.get(i), "scale10");

                        ticketService.saveTicket(inputTicketDto);

                        logger.info("...Bootstrapping ticket table completed for the first 10 days completed.");
                    }
                } else {

                    int remainingSeats = (tripList.get(i).getRemainingSeats()) / 2;

                    for (int j = 0; j < remainingSeats; j++) {

                        InputTicketDto inputTicketDto =

                                new InputTicketDto(faker.name().firstName() + " " + faker.name().lastName(), getFakeEmail(), faker.phoneNumber()
                                        .cellPhone(), tripIdList.get(i), "scale10");

                        ticketService.saveTicket(inputTicketDto);

                        logger.info("...Bootstrapping ticket table completed for the second batch of 10 days completed.");

                    }
                }
            }
        }
    }

    private String getFakeEmail() {
        return faker.rickAndMorty()
                .character()
                .replace(" ", "")
                .replace(".", "")
                .replace("-", "")
                .replace("'", "")
                .replace(",", "")
                .toLowerCase()
                + "@scalemail.com";
    }
}