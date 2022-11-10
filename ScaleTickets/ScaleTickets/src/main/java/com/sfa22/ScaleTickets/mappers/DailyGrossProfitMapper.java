package com.sfa22.ScaleTickets.mappers;


import com.sfa22.ScaleTickets.dtos.DailyGrossProfitDto;

import com.sfa22.ScaleTickets.entities.DailyGrossProfit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyGrossProfitMapper {

    public DailyGrossProfit mapDailyGrossProfitDtoToDailyGrossProfit(DailyGrossProfitDto dailyProfitDto) {

        return new DailyGrossProfit(dailyProfitDto.getGrossProfitId(), dailyProfitDto.getDailyGrossProfit(),
                dailyProfitDto.getGrossProfitDate());


    }

    public DailyGrossProfitDto mapDailyGrossProfitToDailyGrossProfitDto(DailyGrossProfit dailyProfit) {

        return new DailyGrossProfitDto(dailyProfit.getGrossProfitId(), dailyProfit.getDailyGrossProfit(),
                dailyProfit.getGrossProfitDate());
    }

    public List<DailyGrossProfitDto> mapListOfDailyGrossProfitToDailyGrossProfitDto(List<DailyGrossProfit> listOfDailyGrossProfit) {

        return listOfDailyGrossProfit.stream().map(this::mapDailyGrossProfitToDailyGrossProfitDto).collect(Collectors.toList());
    }
}
