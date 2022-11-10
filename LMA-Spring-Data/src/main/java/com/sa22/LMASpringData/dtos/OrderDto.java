package com.sa22.LMASpringData.dtos;

import javax.persistence.*;
import java.time.LocalDate;

public class OrderDto {

    private long orderId;

    private String bookName;

    private String clientName;

    private LocalDate issueDate;

    private LocalDate returnDate;

    public OrderDto() {
    }

    public OrderDto(long orderId, String bookName,
                    String clientName) {
        this.orderId = orderId;
        this.bookName = bookName;
        this.clientName = clientName;
        this.issueDate = LocalDate.now();
        this.returnDate = issueDate.plusDays(30);
    }

    public OrderDto(String bookName,
                    String clientName) {
        this.bookName = bookName;
        this.clientName = clientName;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", BookName='" + bookName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}

