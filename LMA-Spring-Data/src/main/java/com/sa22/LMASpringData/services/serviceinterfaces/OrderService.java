package com.sa22.LMASpringData.services.serviceinterfaces;

import com.sa22.LMASpringData.dtos.OrderDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);

    List<OrderDto> getAllOrders();

    List<OrderDto> getOrderByClientId(long clientId);


    List<OrderDto> getOrdersOnIssueDate(LocalDate issueDate);

    List<OrderDto> getOrdersBeforeIssueDate(LocalDate issueDate);

    List<OrderDto> getOrdersAfterIssueDate(LocalDate issueDate);

    List<OrderDto> getOrdersBeforeReturnDate(LocalDate returnDate);

    List<OrderDto> getOrdersOnReturnDate(LocalDate returnDate);

    List<OrderDto> getOrdersAfterReturnDate(LocalDate returnDate);
}
