package com.sfa22.ScaleTickets.data;

import com.sfa22.ScaleTickets.entities.DailyExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyExpensesRepository extends JpaRepository<DailyExpenses, Integer> {
    Optional<DailyExpenses> findByExpenseDate(LocalDate profitDate);

    List<DailyExpenses> findByExpenseDateGreaterThan(LocalDate profitDate);

    List<DailyExpenses> findByExpenseDateBefore(LocalDate profitDate);

    List<DailyExpenses> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);

}
