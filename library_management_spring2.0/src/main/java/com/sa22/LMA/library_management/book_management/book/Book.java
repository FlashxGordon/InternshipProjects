package com.sa22.LMA.library_management.book_management.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private int id;
    private String bookName;
    private String authorName;
    private Date issueDate; //imported java.sql.Date for SQL date format - YYYY-MM-DD

    Book(String bookName, String authorName, Date issueDate) {
        this.bookName = bookName;
        this.authorName = bookName;
        this.issueDate = issueDate;
    }
}