package com.sfa22.ScaleTickets.data;

import com.sfa22.ScaleTickets.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Optional<Driver> findByPhoneNumber(String phoneNumber);

    List<Driver> findByDailyWageLessThan(double dailyWage);

    List<Driver> findByDailyWageGreaterThan(double dailyWage);

    List<Driver> findByDailyWageEquals(double dailyWage);

    List<Driver> findByFirstNameAndLastName(String firstName, String lastName);

    List<Driver> findByFirstName(String firstName);

    List<Driver> findByLastName(String lastName);
}