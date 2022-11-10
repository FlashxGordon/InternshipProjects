package com.sfa22.ScaleTickets.databasebootstrapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Service
public class BootstrapDataServiceImpl implements InitializingBean {

    Logger logger = LoggerFactory.getLogger(BootstrapDataServiceImpl.class);
    private final BusTable busTable;
    private final RouteTable routeTable;
    private final DiscountCodeTable discountCodeTable;
    private final DriverTable driverTable;
    private final TripTable tripTable;

    private final TicketTable ticketTable;

    public BootstrapDataServiceImpl(BusTable busTable,
                                    RouteTable routeTable,
                                    DiscountCodeTable discountCodeTable,
                                    DriverTable driverTable,
                                    TripTable tripTable, TicketTable ticketTable) {
        this.busTable = busTable;
        this.routeTable = routeTable;
        this.discountCodeTable = discountCodeTable;
        this.driverTable = driverTable;
        this.tripTable = tripTable;
        this.ticketTable = ticketTable;
    }

    /**
     * Method employs InitializedBean in order to make sure that the tables are populated before the application compiles,
     * in a predefined order. The order is defined by the mutual dependencies of said tables.
     * <p>
     * EXAMPLE: Populating the Trip table is impossible without the contents of Bus, Route and Driver tables
     */

    @Override
    @Transactional()
    public void afterPropertiesSet() throws MessagingException {

        logger.info("Checking if bootstrapping data is necessary...");

        routeTable.run();
        busTable.run();
        driverTable.run();
        discountCodeTable.run();
        tripTable.run();
        ticketTable.run();

    }
}



