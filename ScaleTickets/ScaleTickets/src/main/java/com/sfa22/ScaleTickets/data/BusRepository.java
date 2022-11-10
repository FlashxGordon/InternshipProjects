package com.sfa22.ScaleTickets.data;

import com.sfa22.ScaleTickets.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {

    Optional<Bus> findByBusPlate(String plateNumber);

    List<Bus> findByBusCapacityLessThan(int capacity);

    List<Bus> findByBusCapacityGreaterThan(int capacity);

    List<Bus> findByBusCapacityEquals(int capacity);

    List<Bus> findByDailyCostLessThan(double dailyCost);

    List<Bus> findByDailyCostGreaterThan(double dailyCost);

    List<Bus> findByDailyCostEquals(double dailyCost);

    List<Bus> findByBusCapacityEqualsAndDailyCostEquals(int capacity, double dailyCost);
}