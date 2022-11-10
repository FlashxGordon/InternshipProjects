package com.sfa22.ScaleTickets.services.implementations;


import com.sfa22.ScaleTickets.data.DailyProfitRepository;
import com.sfa22.ScaleTickets.data.DailyRevenueRepository;

import com.sfa22.ScaleTickets.dtos.DailyGrossProfitDto;
import com.sfa22.ScaleTickets.entities.DailyGrossProfit;
import com.sfa22.ScaleTickets.entities.DailyRevenue;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.DailyGrossProfitMapper;
import com.sfa22.ScaleTickets.services.interfaces.DailyGrossProfitService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class DailyGrossProfitServiceImpl implements DailyGrossProfitService {

    private final DailyProfitRepository dailyProfitRepository;

    private final DailyRevenueRepository dailyRevenueRepository;

    private final DailyGrossProfitMapper dailyGrossProfitMapper;

    private final DailyExpensesServiceImpl dailyExpensesServiceImpl;

    private final DailyRevenueServiceImpl dailyRevenueService;

    public DailyGrossProfitServiceImpl(DailyProfitRepository dailyProfitRepository,
                                       DailyRevenueRepository dailyRevenueRepository,
                                       DailyGrossProfitMapper dailyGrossProfitMapper,
                                       DailyExpensesServiceImpl dailyExpensesServiceImpl,
                                       DailyRevenueServiceImpl dailyRevenueService) {
        this.dailyProfitRepository = dailyProfitRepository;
        this.dailyRevenueRepository = dailyRevenueRepository;
        this.dailyGrossProfitMapper = dailyGrossProfitMapper;
        this.dailyExpensesServiceImpl = dailyExpensesServiceImpl;
        this.dailyRevenueService = dailyRevenueService;
    }

    @Override
    public List<DailyGrossProfitDto> getAllDailyGrossProfit() {
        List<DailyGrossProfit> dailyGrossProfitList = dailyProfitRepository.findAll();

        return dailyGrossProfitMapper.mapListOfDailyGrossProfitToDailyGrossProfitDto(dailyGrossProfitList);
    }

    @Override
    public DailyGrossProfitDto getByProfitDate(LocalDate profitDate) {

        DailyGrossProfit searchedGrossProfit = dailyProfitRepository.findByGrossProfitDate(profitDate)
                .orElseThrow(() -> new ResourceMissingException("There is no profit record for this date", "Profit"));

        return dailyGrossProfitMapper.mapDailyGrossProfitToDailyGrossProfitDto(searchedGrossProfit);
    }

    @Override
    public List<DailyGrossProfitDto> getByProfitDateGreaterThan(LocalDate profitDate) {

        List<DailyGrossProfit> searchedGrossProfit = dailyProfitRepository.findByGrossProfitDateGreaterThan(profitDate);

        return dailyGrossProfitMapper.mapListOfDailyGrossProfitToDailyGrossProfitDto(searchedGrossProfit);
    }

    @Override
    public List<DailyGrossProfitDto> getByProfitDateBefore(LocalDate profitDate) {

        List<DailyGrossProfit> searchedGrossProfit = dailyProfitRepository.findByGrossProfitDateBefore(profitDate);

        return dailyGrossProfitMapper.mapListOfDailyGrossProfitToDailyGrossProfitDto(searchedGrossProfit);
    }

    @Override
    public List<DailyGrossProfitDto> getByProfitDateBetween(LocalDate startDate, LocalDate endDate) {

        List<DailyGrossProfit> searchedGrossProfit = dailyProfitRepository.findByGrossProfitDateBetween(startDate, endDate);

        return dailyGrossProfitMapper.mapListOfDailyGrossProfitToDailyGrossProfitDto(searchedGrossProfit);
    }

    @Override
    public double calculateDailyGrossProfitForSpecificDate(LocalDate profitDate) {


        Optional<DailyRevenue> dailyRevenue = dailyRevenueRepository.findByIncomeDate(profitDate);

        double dailyIncome = dailyRevenue.get().getDailyIncome();

        double dailyExpenses = dailyExpensesServiceImpl.calculateDailyExpensesForSpecificDate(profitDate);


        return dailyIncome - dailyExpenses;
    }


    @Override
    public double calculateDailyGrossProfitInDateRange(LocalDate startDate, LocalDate endDate) {

        double periodIncome = dailyRevenueService.getTotalDailyRevenueInDateRange(startDate, endDate);

        double periodExpenses = dailyExpensesServiceImpl.calculateDailyExpensesBetween(startDate, endDate);

        return periodIncome - periodExpenses;
    }


    @Override
    public DailyGrossProfitDto updateDailyGrossProfit(LocalDate targetDate) {

        if ((!dailyProfitRepository.findByGrossProfitDate(targetDate).isPresent())) {

            double profitToUpdate = calculateDailyGrossProfitForSpecificDate(targetDate);

            DailyGrossProfit firstProfit = new DailyGrossProfit(profitToUpdate, targetDate);

            firstProfit = dailyProfitRepository.save(firstProfit);

            return dailyGrossProfitMapper.mapDailyGrossProfitToDailyGrossProfitDto(firstProfit);
        }
        DailyGrossProfit currentProfit = dailyProfitRepository.findByGrossProfitDate(targetDate).orElseThrow();

        double updatedProfitSum = calculateDailyGrossProfitForSpecificDate(targetDate);

        currentProfit.setDailyGrossProfit(updatedProfitSum);
        dailyProfitRepository.save(currentProfit);

        return dailyGrossProfitMapper.mapDailyGrossProfitToDailyGrossProfitDto(currentProfit);
    }


}

