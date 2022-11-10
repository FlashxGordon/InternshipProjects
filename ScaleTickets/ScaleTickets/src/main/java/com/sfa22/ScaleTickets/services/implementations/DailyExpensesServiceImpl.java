package com.sfa22.ScaleTickets.services.implementations;

import com.sfa22.ScaleTickets.data.DailyExpensesRepository;
import com.sfa22.ScaleTickets.data.TripRepository;
import com.sfa22.ScaleTickets.dtos.DailyExpensesDto;
import com.sfa22.ScaleTickets.entities.DailyExpenses;
import com.sfa22.ScaleTickets.entities.Route;
import com.sfa22.ScaleTickets.entities.Trip;
import com.sfa22.ScaleTickets.enums.PricePerKm;
import com.sfa22.ScaleTickets.enums.SoftwareCost;
import com.sfa22.ScaleTickets.mappers.DailyExpensesMapper;
import com.sfa22.ScaleTickets.services.interfaces.BusService;
import com.sfa22.ScaleTickets.services.interfaces.DailyExpensesService;
import com.sfa22.ScaleTickets.services.interfaces.DailyRevenueService;
import com.sfa22.ScaleTickets.services.interfaces.DriverService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DailyExpensesServiceImpl implements DailyExpensesService {

    private final DailyExpensesRepository dailyExpensesRepository;

    private final DailyExpensesMapper dailyExpensesMapper;

    private final BusService busService;

    private final TripRepository tripRepository;

    private final DailyRevenueService dailyRevenueService;

    private final DriverService driverService;

    public DailyExpensesServiceImpl(DailyExpensesRepository dailyExpensesRepository,
                                    DailyExpensesMapper dailyExpensesMapper, BusServiceImpl busService,
                                    TripRepository tripRepository, DailyRevenueService dailyRevenueService, DriverService driverService) {

        this.dailyExpensesRepository = dailyExpensesRepository;
        this.dailyExpensesMapper = dailyExpensesMapper;
        this.busService = busService;
        this.tripRepository = tripRepository;
        this.dailyRevenueService = dailyRevenueService;
        this.driverService = driverService;
    }

    @Override
    public DailyExpensesDto findByExpenseDate(LocalDate profitDate) {

        Optional<DailyExpenses> searchedDailyExpense = dailyExpensesRepository.findByExpenseDate(profitDate);

        return dailyExpensesMapper.mapDailyExpensesToDailyExpensesDto(searchedDailyExpense.orElseThrow());
    }

    @Override
    public List<DailyExpensesDto> getByExpenseDateGreaterThan(LocalDate profitDate) {

        List<DailyExpenses> searchedDailyExpenses = dailyExpensesRepository.findByExpenseDateGreaterThan(profitDate);

        return dailyExpensesMapper.mapListOfDailyExpensesToDailyExpensesDto(searchedDailyExpenses);
    }

    @Override
    public List<DailyExpensesDto> getByExpenseDateBefore(LocalDate profitDate) {

        List<DailyExpenses> searchedDailyExpenses = dailyExpensesRepository.findByExpenseDateBefore(profitDate);

        return dailyExpensesMapper.mapListOfDailyExpensesToDailyExpensesDto(searchedDailyExpenses);
    }

    @Override
    public List<DailyExpensesDto> getByExpenseDateBetween(LocalDate startDate, LocalDate endDate) {

        List<DailyExpenses> searchedDailyExpenses = dailyExpensesRepository.findByExpenseDateBetween(startDate, endDate);

        return dailyExpensesMapper.mapListOfDailyExpensesToDailyExpensesDto(searchedDailyExpenses);
    }

    @Override
    public double calculateDailyExpensesBetween(LocalDate startDate, LocalDate endDate) {

        List<DailyExpenses> searchedListDto = dailyExpensesRepository.findByExpenseDateBetween(startDate, endDate);

        double fixCostSum = searchedListDto.stream().mapToDouble(DailyExpenses::getDailyFixedCost).sum();

        double fuelCostSum = searchedListDto.stream().mapToDouble(DailyExpenses::getDailyFuelCost).sum();

        double softwareCost = searchedListDto.stream().mapToDouble(DailyExpenses::getDailySoftwareCost).sum();

        double sumDailyExpensesBetween = fixCostSum + fuelCostSum + softwareCost;

        return sumDailyExpensesBetween;

    }

    @Override
    public double calculateDailyExpensesForSpecificDate(LocalDate profitDate) {

        double dailyFixCost = calculateDailyFixCost();

        double dailyFuelCost = calculateDailyFuelCost(profitDate);

        double dailySoftWareCost = calculateDailySoftwareCost(profitDate);

        double dailyExpenses = dailyFixCost + dailyFuelCost + dailySoftWareCost;

        doesDailyExpenseWithDateExists(profitDate, dailyFixCost, dailyFuelCost, dailySoftWareCost);

        return dailyExpenses;
    }

    private void doesDailyExpenseWithDateExists(LocalDate profitDate, double dailyFixCost,
                                                double dailyFuelCost, double dailySoftwareCost) {

        List<DailyExpenses> allExpenses = dailyExpensesRepository.findAll();

        boolean isDailyExpenseExists =
                allExpenses.stream().anyMatch(dailyExpenses -> dailyExpenses.getExpenseDate().equals(profitDate));

        if (isDailyExpenseExists) {

            Optional<DailyExpenses> existingDailyExpense = dailyExpensesRepository.findByExpenseDate(profitDate);

            existingDailyExpense.get().setDailyFixedCost(dailyFixCost);
            existingDailyExpense.get().setDailyFuelCost(dailyFuelCost);
            existingDailyExpense.get().setDailySoftwareCost(dailySoftwareCost);

            dailyExpensesRepository.save(existingDailyExpense.get());

        } else {

            DailyExpenses newDailyExpense = new DailyExpenses(dailyFixCost, dailyFuelCost, dailySoftwareCost, profitDate);

            dailyExpensesRepository.save(newDailyExpense);
        }
    }

    private double calculateDailySoftwareCost(LocalDate profitDate) {

        double dailyRevenue = dailyRevenueService.getDailyRevenueIncomeByDate(profitDate);

        double dailySoftwareCost = dailyRevenue * SoftwareCost.SOFTWARE_COST.getPricePerKm();

        return dailySoftwareCost;
    }

    private double calculateDailyFixCost() {

        double dailyCostsOfAllBuses = busService.getDailyCostOfAllBuses();

        double dailyWagesOfAllDrivers = driverService.calculateDailyWage();

        double dailyFixCost = dailyCostsOfAllBuses + dailyWagesOfAllDrivers;

        return dailyFixCost;
    }

    private double calculateDailyFuelCost(LocalDate specificDate) {

        double sumOfRouteDistances = getDistanceSumOfRoutesOnGSpecificDate(specificDate);

        double dailyFuelCost = sumOfRouteDistances * PricePerKm.PRICE_PER_KM.getPricePerKm();

        return dailyFuelCost;
    }

    private double getDistanceSumOfRoutesOnGSpecificDate(LocalDate specificDate) {

        List<Trip> searchedTrips = tripRepository.findByDepartureDate(specificDate);

        List<Route> searchedRoutes = searchedTrips.stream().map(Trip::getRoute).collect(Collectors.toList());

        double sumOfRouteDistances = searchedRoutes.stream().mapToDouble(Route::getDistance).sum();

        return sumOfRouteDistances;
    }
}