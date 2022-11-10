package com.sfa22.ScaleTickets.data;

import com.sfa22.ScaleTickets.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountCodeRepository extends JpaRepository<Discount, Integer> {
    Optional<Discount> findByCode(String code);

    List<Discount> findByExpirationDateGreaterThanEqual(LocalDate expirationDate);

    List<Discount> findByExpirationDateLessThan(LocalDate expirationDate);

    Optional<Discount> findByCodeAndExpirationDateGreaterThanEqual(String code, LocalDate expirationDate);

}
