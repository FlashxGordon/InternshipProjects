package com.sfa22.ScaleTickets.services.implementations;

import com.sfa22.ScaleTickets.data.DailyRevenueRepository;
import com.sfa22.ScaleTickets.data.TicketRepository;
import com.sfa22.ScaleTickets.dtos.DailyRevenueDto;
import com.sfa22.ScaleTickets.entities.DailyRevenue;
import com.sfa22.ScaleTickets.entities.Ticket;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.DailyRevenueMapper;
import com.sfa22.ScaleTickets.services.interfaces.DailyRevenueService;
import com.sfa22.ScaleTickets.validations.DailyRevenueValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DailyRevenueServiceImpl implements DailyRevenueService {

    private final DailyRevenueMapper dailyRevenueMapper;
    private final DailyRevenueRepository dailyRevenueRepository;
    private final TicketRepository ticketRepository;
    private final DailyRevenueValidation dailyRevenueValidation;

    public DailyRevenueServiceImpl(DailyRevenueMapper dailyRevenueMapper,
                                   DailyRevenueRepository dailyRevenueRepository,
                                   TicketRepository ticketRepository, DailyRevenueValidation dailyRevenueValidation) {
        this.dailyRevenueMapper = dailyRevenueMapper;
        this.dailyRevenueRepository = dailyRevenueRepository;
        this.ticketRepository = ticketRepository;
        this.dailyRevenueValidation = dailyRevenueValidation;
    }


    /**
     * Method updates the daily revenue sum for the current date when called by the CalculateService
     *
     * @param targetDate provided by CalculateService
     * @return updatedRevenueInsert (mapped to dto)
     */


    @Override
    public DailyRevenueDto updateDailyRevenue(LocalDate targetDate) {

        if ((dailyRevenueRepository.findByIncomeDate(targetDate).isEmpty())) {
            double ticketPrice = getTicketPrice(targetDate);

            DailyRevenue firstRevenue = new DailyRevenue(ticketPrice, targetDate);
            firstRevenue = dailyRevenueRepository.save(firstRevenue);

            return dailyRevenueMapper.mapDailyRevenueToDailyRevenueDto(firstRevenue);
        }
        Optional<DailyRevenue> currentRevenue =
                dailyRevenueRepository.findByIncomeDate(targetDate);


        double updatedRevenueSum = getTicketPrice(targetDate);

        DailyRevenue updateRevenueInsert = new DailyRevenue(currentRevenue.get().getRevenueId(),
                updatedRevenueSum,
                targetDate);

        dailyRevenueRepository.save(updateRevenueInsert);

        return dailyRevenueMapper.mapDailyRevenueToDailyRevenueDto(updateRevenueInsert);
    }

    /**
     * Private method to extract and
     * sum ticket prices from the ticket repository
     *
     * @return double sum of ticket prices
     */

    private double getTicketPrice(LocalDate date) {
        return ticketRepository.findAll().stream().
                filter(ticket -> ticket.getTrip()
                        .getDepartureDate().toLocalDate().equals(date))
                .mapToDouble(Ticket::getTicketPrice).sum();
    }


    /**
     * Method returns a list of daily revenues
     * in order to present the information to the user (admin)
     *
     * @return listOfAllDailyRevenues (mapped to dto)
     */

    @Override
    public List<DailyRevenueDto> getAllDailyRevenues() {
        List<DailyRevenue> dailyRevenueList = dailyRevenueRepository.findAll();

        return dailyRevenueMapper.listOfDailyRevenueToDailyRevenueDto(dailyRevenueList);
    }

    /**
     * Method returns a single daily revenue entry
     * in order to present the information to the user (admin)
     *
     * @param revenueId id provide by user (admin)
     * @return single daily revenue entry (mapped to dto)
     */

    @Override
    public DailyRevenueDto getByRevenueId(Integer revenueId) {

        Optional<DailyRevenue> dailyRevenue = dailyRevenueRepository.getByRevenueId(revenueId);

        return dailyRevenueMapper.mapDailyRevenueToDailyRevenueDto(dailyRevenue.orElseThrow(() ->
                new ResourceMissingException("Revenue entry missing", "Revenue")));
    }

    /**
     * Method returns the sum of all revenues for a given date
     * in order to present the information to the user (admin)
     *
     * @param incomeDate date provided by user (admin)
     * @return listOfAllDailyRevenues (mapped to dto)
     */

    @Override
    public DailyRevenueDto getDailyRevenueByDate(LocalDate incomeDate) {

        Optional<DailyRevenue> dailyRevenue = dailyRevenueRepository.findByIncomeDate(incomeDate);

        return dailyRevenueMapper.mapDailyRevenueToDailyRevenueDto(dailyRevenue.orElseThrow(() ->
                new ResourceMissingException("No revenue data present for this date", "Daily revenue entry")));
    }


    /**
     * Method returns the total sum of all revenues for a given date range
     * in order to present the information to the user (admin)
     *
     * @param startDate used to determine start of date range - provided by user (admin)
     * @param endDate   used to determine end of date range - provided by user (admin)
     * @return double total sum of all revenues in date range
     */

    @Override
    public Double getTotalDailyRevenueInDateRange(LocalDate startDate, LocalDate endDate) {

        isDateRangeInputInCorrectOrder(startDate, endDate);

        List<DailyRevenue> dailyRevenueInDateRange = dailyRevenueRepository.findByIncomeDateBetween(startDate, endDate);

        List<Double> listOfRevenueSumsInDateRange =
                dailyRevenueInDateRange.stream().map(DailyRevenue::getDailyIncome).collect(Collectors.toList());

        return listOfRevenueSumsInDateRange.stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     * Method returns a list of all revenues for a given date range
     * in order to present the information to the user (admin)
     *
     * @param startDate used to determine start of date range - provided by user (admin)
     * @param endDate   used to determine end of date range - provided by user (admin)
     * @return list of all revenues for given range (mapped to dto)
     */

    @Override
    public List<DailyRevenueDto> getListOfDailyRevenuesInDateRange(LocalDate startDate, LocalDate endDate) {

        isDateRangeInputInCorrectOrder(startDate, endDate);

        List<DailyRevenue> dailyRevenueInDateRange = dailyRevenueRepository.findByIncomeDateBetween(startDate, endDate);

        return dailyRevenueMapper.listOfDailyRevenueToDailyRevenueDto(dailyRevenueInDateRange);
    }

    /**
     * Method retrieves a double value which is the total of revenue income for
     * the targetDate
     *
     * @param targetDate date by which the income is retrieved
     * @return double value - income generated
     */
    @Override
    public Double getDailyRevenueIncomeByDate(LocalDate targetDate) {

        Optional<DailyRevenue> dailyRevenueInDateRange =
                Optional.ofNullable(dailyRevenueRepository.findByIncomeDate(targetDate).orElseThrow(() ->
                        new ResourceMissingException("No revenue data present for this date", "Daily revenue entry")));

        return dailyRevenueInDateRange.get().getDailyIncome();
    }

    private void isDateRangeInputInCorrectOrder(LocalDate startDate, LocalDate endDate) {

        if (!dailyRevenueValidation.isDateRangeInputInCorrectOrder(startDate, endDate)) {
            throw new InvalidUserInputException("The start date of the date range cannot be after the end date.");
        }
    }
}
