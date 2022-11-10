//package com.sfa22.ScaleTickets.services.implementations;
//
//import com.sfa22.ScaleTickets.data.DailyRevenueRepository;
//import com.sfa22.ScaleTickets.data.TicketRepository;
//import com.sfa22.ScaleTickets.dtos.DailyRevenueDto;
//import com.sfa22.ScaleTickets.entities.DailyRevenue;
//import com.sfa22.ScaleTickets.entities.Ticket;
//import com.sfa22.ScaleTickets.mappers.DailyRevenueMapper;
//import com.sfa22.ScaleTickets.services.interfaces.DailyRevenueService;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class DailyRevenueServiceImpl implements DailyRevenueService {
//
//    private final DailyRevenueMapper dailyRevenueMapper;
//    private final DailyRevenueRepository dailyRevenueRepository;
//    private final TicketRepository ticketRepository;
//    private final ModelMapper modelMapper;
//
//    public DailyRevenueServiceImpl(DailyRevenueMapper dailyRevenueMapper,
//                                   DailyRevenueRepository dailyRevenueRepository,
//                                   TicketRepository ticketRepository, ModelMapper modelMapper) {
//        this.dailyRevenueMapper = dailyRevenueMapper;
//        this.dailyRevenueRepository = dailyRevenueRepository;
//        this.ticketRepository = ticketRepository;
//        this.modelMapper = modelMapper;
//    }
//
//
//    /**
//     * Method updates the daily revenue sum for the current date when called by the CalculateService
//     *
//     * @param targetDate provided by CalculateService
//     * @return updatedRevenueInsert (mapped to dto)
//     */
//
//
//    @Override
//    public DailyRevenueDto updateDailyRevenue(LocalDate targetDate) {
//
//        if ((dailyRevenueRepository.findByIncomeDate(targetDate) == null)) {
//            double ticketPrice = getTicketPrice();
//
//            DailyRevenue firstRevenue = new DailyRevenue(ticketPrice, LocalDate.now());
//            dailyRevenueRepository.save(firstRevenue);
//        }
//        DailyRevenue currentRevenue =
//                dailyRevenueRepository.findByIncomeDate(targetDate);
//
//
//        double updatedRevenueSum = getTicketPrice();
//
//        DailyRevenue updateRevenueInsert = new DailyRevenue(currentRevenue.getRevenueId(),
//                updatedRevenueSum,
//                targetDate);
//
//        dailyRevenueRepository.save(updateRevenueInsert);
//
//        return  dailyRevenueMapper.mapDailyRevenueToDailyRevenueDto(updateRevenueInsert);
//        //mapToDTO(updateRevenueInsert);
//    }
//
//    /**
//     * Method to extract and
//     * sum ticket prices from the ticket repository
//     *
//     * @return double sum of ticket prices
//     */
//
//    private double getTicketPrice() {
//        double ticketPrice = ticketRepository.findAll().stream().
//                filter(ticket -> ticket.getTrip()
//                        .getDepartureDate().toLocalDate().equals(LocalDate.now()))
//                .mapToDouble(Ticket::getTicketPrice).sum();
//        return ticketPrice;
//    }
//
//
//    /**
//     * Using the HTTP GET method returns a list of daily revenues
//     * in order to present the information to the user (admin)
//     *
//     * @return listOfAllDailyRevenues (mapped to dto)
//     */
//
//    @Override
//    public List<DailyRevenueDto> getAllDailyRevenues() {
//        List<DailyRevenue> dailyRevenueList = dailyRevenueRepository.findAll();
//
//        return  dailyRevenueMapper.listOfDailyRevenueToDailyRevenueDto(dailyRevenueList);
//        //dailyRevenueList.stream().map(this::mapToDTO).collect(Collectors.toList());
//    }
//
//    /**
//     * Using the HTTP GET method returns the sum of all revenues for a given date
//     * in order to present the information to the user (admin)
//     *
//     * @param incomeDate date provided by user (admin)
//     * @return listOfAllDailyRevenues (mapped to dto)
//     */
//
//    @Override
//    public DailyRevenueDto getDailyRevenueByDate(LocalDate incomeDate) {
//
//        DailyRevenue dailyRevenue = dailyRevenueRepository.findByIncomeDate(incomeDate);
//
//        return  dailyRevenueMapper.mapDailyRevenueToDailyRevenueDto(dailyRevenue);
//        //mapToDTO(dailyRevenue);
//    }
//
//
//    /**
//     * Using the HTTP GET method returns the total sum of all revenues for a given date range
//     * in order to present the information to the user (admin)
//     *
//     * @param startDate used to determine start of date range - provided by user (admin)
//     * @param endDate   used to determine end of date range - provided by user (admin)
//     * @return double total sum of all revenues in date range
//     */
//
//    @Override
//    public Double getTotalDailyRevenueInDateRange(LocalDate startDate, LocalDate endDate) {
//
//        List<DailyRevenue> dailyRevenueInDateRange = dailyRevenueRepository.findByIncomeDateBetween(startDate, endDate);
//
//        List<Double> listOfRevenueSumsInDateRange =
//                dailyRevenueInDateRange.stream().map(DailyRevenue::getDailyIncome).collect(Collectors.toList());
//
//        return listOfRevenueSumsInDateRange.stream().mapToDouble(Double::doubleValue).sum();
//    }
//
//    /**
//     * Using the HTTP GET method returns a list of all revenues for a given date range
//     * in order to present the information to the user (admin)
//     *
//     * @param startDate used to determine start of date range - provided by user (admin)
//     * @param endDate   used to determine end of date range - provided by user (admin)
//     * @return list of all revenues for given range (mapped to dto)
//     */
//
//    @Override
//    public List<DailyRevenueDto> getListOfDailyRevenuesInDateRange(LocalDate startDate, LocalDate endDate) {
//
//        List<DailyRevenue> dailyRevenueInDateRange = dailyRevenueRepository.findByIncomeDateBetween(startDate, endDate);
//
//        return  dailyRevenueMapper.listOfDailyRevenueToDailyRevenueDto(dailyRevenueInDateRange);
//        //dailyRevenueInDateRange.stream().map(this::mapToDTO).collect(Collectors.toList());
//    }
//
//    /**
//     * Method retrieves a double value which is the total of revenue income for
//     * the targetDate
//     *
//     * @param targetDate date by which the income is retrieved
//     * @return double value - income generated
//     */
//    @Override
//    public Double getDailyRevenueIncomeByDate(LocalDate targetDate) {
//
//        DailyRevenue dailyRevenueInDateRange = dailyRevenueRepository.findByIncomeDate(targetDate);
//
//        return dailyRevenueInDateRange.getDailyIncome();
//    }
//
//
//    private DailyRevenueDto mapToDTO(DailyRevenue dailyRevenue) {
//        return modelMapper.map(dailyRevenue, DailyRevenueDto.class);
//    }
//
//    private DailyRevenue mapToEntity(DailyRevenueDto dailyRevenueDto) {
//        return modelMapper.map(dailyRevenueDto, DailyRevenue.class);
//    }
//
//}
