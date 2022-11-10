package com.sa22.LMASpringData.controllers;

import com.sa22.LMASpringData.dtos.OrderDto;
import com.sa22.LMASpringData.services.serviceinterfaces.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/orders")
    public List<OrderDto> getAllOrders() throws NoSuchElementException {
        return ResponseEntity.ok(orderService.getAllOrders()).getBody();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/client/{clientId}/orders")
    public List<OrderDto> getOrdersByClientName(@PathVariable long clientId) throws NoSuchElementException {

        return ResponseEntity.ok(orderService.getOrderByClientId(clientId)).getBody();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/orders")
    public ResponseEntity<OrderDto> createBook(@RequestBody OrderDto orderDto) {


        OrderDto savedOrder = orderService.createOrder(orderDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOrder.getOrderId()).toUri();


        return ResponseEntity.created(location).build();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/orders", params = {"onIssueDate"})
    public List<OrderDto> getOrdersOnIssueDate(@RequestParam(name = "onIssueDate") String issueDate) throws NoSuchElementException {
        return ResponseEntity.ok(orderService.getOrdersOnIssueDate(LocalDate.parse(issueDate))).getBody();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/orders", params = {"beforeIssueDate"})
    public List<OrderDto> getOrdersBeforeIssueDate(@RequestParam(name = "beforeIssueDate") String issueDate) throws NoSuchElementException {
        return ResponseEntity.ok(orderService.getOrdersBeforeIssueDate(LocalDate.parse(issueDate))).getBody();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/orders", params = {"afterIssueDate"})
    public List<OrderDto> getOrdersAfterIssueDate(@RequestParam(name = "afterIssueDate") String issueDate) throws NoSuchElementException {
        return ResponseEntity.ok(orderService.getOrdersAfterIssueDate(LocalDate.parse(issueDate))).getBody();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/orders", params = {"onReturnDate"})
    public List<OrderDto> getOrdersOnReturnDate(@RequestParam(name = "onReturnDate") String returnDate) throws NoSuchElementException {
        return ResponseEntity.ok(orderService.getOrdersOnReturnDate(LocalDate.parse(returnDate))).getBody();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/orders", params = {"beforeReturnDate"})
    public List<OrderDto> getOrdersBeforeReturnDate(@RequestParam(name = "beforeReturnDate") String returnDate) throws NoSuchElementException {
        return ResponseEntity.ok(orderService.getOrdersBeforeReturnDate(LocalDate.parse(returnDate))).getBody();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/orders", params = {"afterReturnDate"})
    public List<OrderDto> getOrdersAfterReturnDate(@RequestParam(name = "afterReturnDate") String returnDate) throws NoSuchElementException {
        return ResponseEntity.ok(orderService.getOrdersAfterReturnDate(LocalDate.parse(returnDate))).getBody();
    }

}
