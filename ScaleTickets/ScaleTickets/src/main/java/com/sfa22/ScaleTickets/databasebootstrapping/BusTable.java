package com.sfa22.ScaleTickets.databasebootstrapping;

import com.sfa22.ScaleTickets.data.BusRepository;
import com.sfa22.ScaleTickets.entities.Bus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusTable implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(BusTable.class);
    private final BusRepository busRepository;

    public BusTable(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    /**
     * Method which inserts predetermined values into the database
     * the purpose of inserting the information is to  automatically insert data upon server startup so
     * that the application has data that it can manipulate for demonstration purposes
     * <p>
     * This particular method populates the bus table with the details of 2 buses
     *
     * @param args
     */

    @Override
    public void run(String... args) {

        List<Bus> busList = busRepository.findAll();

        if (busList.isEmpty()) {

            logger.info("Bootstrapping bus table...");

            busRepository.save(new Bus("CA1234MK", 30, 200));
            busRepository.save(new Bus("PB1213MK", 18, 100));

            logger.info("...Bootstrapping bus table completed");
        }
    }
}
