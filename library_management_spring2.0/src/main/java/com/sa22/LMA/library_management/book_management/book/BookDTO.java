package com.sa22.LMA.library_management.book_management.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    public int bookId;
    public String bookName;
    public String authorName;
    public Date issueDate;

    public BookDTO(String bookName, String authorName, Date issueDate){
        this.bookName = bookName;
        this.authorName = authorName;
        this.issueDate = issueDate;
    }

}
