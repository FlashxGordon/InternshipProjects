package com.sa22.LMASpringData.services;

import com.sa22.LMASpringData.dtos.OrderDto;
import com.sa22.LMASpringData.entities.OrderEntity;
import com.sa22.LMASpringData.repositories.OrderRepository;
import com.sa22.LMASpringData.services.serviceinterfaces.OrderService;
import com.sa22.LMASpringData.validations.ClientValidations;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientValidations clientValidations;
    private final ModelMapper mapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ModelMapper mapper,
                            ClientValidations clientValidations) {
        this.clientValidations = clientValidations;
        this.orderRepository = orderRepository;
        this.mapper = mapper;

    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        if (clientValidations.isClientFound(orderDto.getClientName())) {
            OrderEntity orderEntity = mapToEntity(orderDto);
            OrderEntity newOrderEntity = orderRepository.save(orderEntity);

            return mapToDTO(newOrderEntity);

        } else throw new NoSuchElementException();
    }

    @Override
    public List<OrderDto> getAllOrders()
    {
        List<OrderEntity> orderEntityList = orderRepository.findAll();

        return orderEntityList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrderByClientId(long clientId) {

        if (clientValidations.isClientFound(clientId)) {
            List<OrderEntity> orderEntityList = orderRepository.findAllById(Collections.singleton(clientId));

            return orderEntityList.stream().map(this::mapToDTO).collect(Collectors.toList());

        } else throw new NoSuchElementException();
    }


    @Override
    public List<OrderDto> getOrdersOnIssueDate(LocalDate issueDate) {

        List<OrderEntity> orderEntityList = orderRepository.findOrdersByIssueDate(issueDate);
        List<OrderEntity> issueDateList = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getIssueDate().isEqual(issueDate)) {
                issueDateList.add(orderEntity);
            }
        }

        return issueDateList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersBeforeIssueDate(LocalDate issueDate) {
        List<OrderEntity> orderEntityList = orderRepository.findOrdersByIssueDateLessThan(issueDate);
        List<OrderEntity> issueDateList = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getIssueDate().isBefore(issueDate)) {
                issueDateList.add(orderEntity);
            }
        }

        return issueDateList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersAfterIssueDate(LocalDate issueDate) {

        List<OrderEntity> orderEntityList = orderRepository.findOrdersByIssueDateGreaterThan(issueDate);
        List<OrderEntity> issueDateList = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getIssueDate().isAfter(issueDate)) {
                issueDateList.add(orderEntity);
            }
        }

        return issueDateList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersBeforeReturnDate(LocalDate returnDate) {

        List<OrderEntity> orderEntityList = orderRepository.findOrdersByReturnDate(returnDate);
        List<OrderEntity> returnDateList = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getIssueDate().isBefore(returnDate)) {
                returnDateList.add(orderEntity);
            }
        }
        return returnDateList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    @Override
    public List<OrderDto> getOrdersOnReturnDate(LocalDate returnDate) {

        List<OrderEntity> orderEntityList = orderRepository.findOrdersByReturnDate(returnDate);
        List<OrderEntity> returnDateList = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getIssueDate().isEqual(returnDate)) {
                returnDateList.add(orderEntity);
            }
        }

        return returnDateList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    @Override
    public List<OrderDto> getOrdersAfterReturnDate(LocalDate returnDate) {

        List<OrderEntity> orderEntityList = orderRepository.findOrdersByIssueDateGreaterThan(returnDate);
        List<OrderEntity> returnDateList = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getIssueDate().isAfter(returnDate)) {
                returnDateList.add(orderEntity);
            }
        }

        return returnDateList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    // convert Entity into DTO
    private OrderDto mapToDTO(OrderEntity orderEntity) {
        return mapper.map(orderEntity, OrderDto.class);
    }

    // convert DTO to entity
    private OrderEntity mapToEntity(OrderDto orderDto) {
        return mapper.map(orderDto, OrderEntity.class);
    }

}
