package com.sa22.LMASpringData.repositories;

import com.sa22.LMASpringData.entities.OrderEntity;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

//    List<OrderEntity> findOrderByClientName(String name);

    List<OrderEntity> findOrdersByIssueDate(LocalDate issueDate);

    List<OrderEntity> findOrdersByIssueDateGreaterThan(LocalDate issueDate);

    List<OrderEntity> findOrdersByIssueDateLessThan(LocalDate issueDate);

    List<OrderEntity> findOrdersByReturnDate(LocalDate returnDate);

    List<OrderEntity> findOrdersByReturnDateGreaterThan(LocalDate returnDate);

    List<OrderEntity> findOrdersByReturnDateLessThan(LocalDate returnDate);

}
