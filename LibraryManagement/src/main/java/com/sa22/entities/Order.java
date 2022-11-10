package com.sa22.entities;

import java.time.LocalDate;

public class Order {

    private String bookChoice;
    private String authorName;
    private LocalDate issueDate;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private String clientName;

    public Order(String bookChoice,
                 String authorName,
                 LocalDate issueDate,
                 LocalDate borrowDate,
                 LocalDate dueDate,
                 String clientName) {

        this.bookChoice = bookChoice;
        this.authorName = authorName;
        this.issueDate = issueDate;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.clientName = clientName;
    }

    public String getBookChoice() {
        return bookChoice;
    }

    public void setBookChoice(String bookChoice) {
        this.bookChoice = bookChoice;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


    @Override
    public String toString() {
        return "Order: " +
                "bookChoice='" + bookChoice + '\'' +
                ", authorName='" + authorName + '\'' +
                ", issueDate=" + issueDate +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
