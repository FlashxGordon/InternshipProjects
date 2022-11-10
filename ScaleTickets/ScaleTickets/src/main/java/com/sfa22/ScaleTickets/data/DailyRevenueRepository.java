package com.sfa22.ScaleTickets.data;

import com.sfa22.ScaleTickets.entities.DailyRevenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyRevenueRepository extends JpaRepository<DailyRevenue, Integer> {

    Optional<DailyRevenue> findByIncomeDate(LocalDate incomeDate);

    List<DailyRevenue> findByIncomeDateGreaterThan(LocalDate incomeDate);

    List<DailyRevenue> findByIncomeDateBefore(LocalDate incomeDate);

    List<DailyRevenue> findByIncomeDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<DailyRevenue> getByRevenueId(Integer revenueId);
}
