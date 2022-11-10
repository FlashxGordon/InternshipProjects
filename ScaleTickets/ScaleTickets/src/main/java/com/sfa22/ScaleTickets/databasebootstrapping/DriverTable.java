package com.sfa22.ScaleTickets.databasebootstrapping;

import com.sfa22.ScaleTickets.data.DriverRepository;
import com.sfa22.ScaleTickets.entities.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DriverTable implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(DriverTable.class);
    private final DriverRepository driverRepository;

    public DriverTable(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    /**
     * Method which inserts predetermined values into the database
     * the purpose of inserting the information is to  automatically insert data upon server startup so
     * that the application has data that it can manipulate for demonstration purposes
     * <p>
     * This particular method populates the driver table with predetermined driver information details
     *
     * @param args
     */
    @Override
    public void run(String... args) {

        List<Driver> driverList = driverRepository.findAll();

        if (driverList.isEmpty()) {

            logger.info("Bootstrapping drivers table...");

            driverRepository.save(new Driver("Preslav", "Petrov", "+359898932102", 100));
            driverRepository.save(new Driver("Simeon", "Simeonov", "+359878932102", 100));
            driverRepository.save(new Driver("Dimitar", "Chardakliev", "+359898732102", 100));
            driverRepository.save(new Driver("Erkan", "Kamber", "+359898932101", 100));

            logger.info("...Bootstrapping drivers table completed");
        }
    }
}
