package com.sa22.LMASpringData.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "order_table")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "book_name")
    private String bookName;
    @Column(name = "client_name")
    private String clientName;
    @Column(name = "issue_date")
    private LocalDate issueDate;
    @Column(name = "return_date")
    private LocalDate returnDate;

    public OrderEntity() {
    }

    public OrderEntity(String bookName,
                       String clientName,
                       LocalDate issueDate,
                       LocalDate returnDate) {
        this.bookName = bookName;
        this.clientName = clientName;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
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
        this.issueDate = LocalDate.now();
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = issueDate.plusDays(30);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "orderId=" + orderId +
                ", BookName='" + bookName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
