package com.sfa22.ScaleTickets.data;

import com.sfa22.ScaleTickets.entities.DailyGrossProfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyProfitRepository extends JpaRepository<DailyGrossProfit, Integer> {

    Optional<DailyGrossProfit> findByGrossProfitDate(LocalDate profitDate);

    List<DailyGrossProfit> findByGrossProfitDateGreaterThan(LocalDate profitDate);

    List<DailyGrossProfit> findByGrossProfitDateBefore(LocalDate profitDate);

    List<DailyGrossProfit> findByGrossProfitDateBetween(LocalDate startDate, LocalDate endDate);

}
