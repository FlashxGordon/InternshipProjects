package com.sfa22.ScaleTickets.mappers;

import com.sfa22.ScaleTickets.dtos.DailyRevenueDto;
import com.sfa22.ScaleTickets.entities.DailyRevenue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyRevenueMapper {

    public DailyRevenue mapDailyRevenueDtoToDailyRevenue(DailyRevenueDto dailyRevenueDto) {

        return new DailyRevenue(dailyRevenueDto.getRevenueId(), dailyRevenueDto.getDailyIncome(),
                dailyRevenueDto.getIncomeDate());
    }

    public DailyRevenueDto mapDailyRevenueToDailyRevenueDto(DailyRevenue dailyRevenue) {

        return new DailyRevenueDto(dailyRevenue.getRevenueId(), dailyRevenue.getDailyIncome(),
                dailyRevenue.getIncomeDate());
    }

    public List<DailyRevenueDto> listOfDailyRevenueToDailyRevenueDto(List<DailyRevenue> listOfDailyRevenue) {

        return listOfDailyRevenue.stream().map(this::mapDailyRevenueToDailyRevenueDto).collect(Collectors.toList());
    }
}
