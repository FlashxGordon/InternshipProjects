package com.sa22.services.interfaces;

import com.sa22.entities.Book;
import com.sa22.entities.Order;

import java.util.List;

public interface OrderServiceInterface {

    List<Order> mapOrder(String filePath);

     String getBorrowDate();

    String getDueDate();
}
