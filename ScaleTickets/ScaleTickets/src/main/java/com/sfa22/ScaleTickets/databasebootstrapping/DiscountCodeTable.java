package com.sfa22.ScaleTickets.databasebootstrapping;

import com.sfa22.ScaleTickets.data.DiscountCodeRepository;
import com.sfa22.ScaleTickets.entities.Discount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DiscountCodeTable implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(DiscountCodeTable.class);
    private final DiscountCodeRepository discountCodeRepository;

    public DiscountCodeTable(DiscountCodeRepository discountCodeRepository) {
        this.discountCodeRepository = discountCodeRepository;
    }

    /**
     * Method which inserts predetermined values into the database
     * the purpose of inserting the information is to  automatically insert data upon server startup so
     * that the application has data that it can manipulate for demonstration purposes
     * <p>
     * This particular method populates the discount code table with predetermined codes
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        List<Discount> discountList = discountCodeRepository.findAll();

        if (discountList.isEmpty()) {

            logger.info("Bootstrapping discount code table...");

            discountCodeRepository.save(new Discount(10,
                    "scale10",
                    LocalDate.of(2022, 10, 15)));
            discountCodeRepository.save(new Discount(20,
                    "tickets20",
                    LocalDate.of(2022, 10, 15)));

            logger.info("...Bootstrapping discount code table completed");

        }
    }
}
