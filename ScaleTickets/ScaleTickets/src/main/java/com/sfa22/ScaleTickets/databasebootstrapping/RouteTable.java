package com.sfa22.ScaleTickets.databasebootstrapping;

import com.sfa22.ScaleTickets.data.RouteRepository;
import com.sfa22.ScaleTickets.entities.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.List;

@Component
public class RouteTable implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(RouteTable.class);
    private final RouteRepository routeRepository;

    public RouteTable(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    /**
     * Method which inserts predetermined values into the database
     * the purpose of inserting the information is to  automatically insert data upon server startup so
     * that the application has data that it can manipulate for demonstration purposes
     * <p>
     * This particular method populates the route table with predetermined routes
     *
     * @param args
     */


    @Override
    public void run(String... args) {

        List<Route> routeList = routeRepository.findAll();

        if (routeList.isEmpty()) {

            logger.info("Bootstrapping route table...");

            routeRepository.save(new Route("Sofia",
                    "Varna", 80, Duration.ofHours(6), 441));
            routeRepository.save(new Route("Varna",
                    "Sofia", 80, Duration.ofHours(6), 441));
            routeRepository.save(new Route("Sofia",
                    "Burgas", 50, Duration.ofHours(4), 383));
            routeRepository.save(new Route("Burgas",
                    "Sofia", 50, Duration.ofHours(4), 383));

            logger.info("...Bootstrapping route table completed");
        }

    }
}
