package com.sa22.LMASpringData.dtos;

import java.sql.Date;

public class BookDto {

    private long bookId;

    private String bookName;

    private String authorName;
    private Date dateOfPublishing;

    public BookDto() {
    }

    public BookDto(long bookId, String bookName, String authorName, Date dateOfPublishing) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.dateOfPublishing = dateOfPublishing;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getDateOfPublishing() {
        return dateOfPublishing;
    }

    public void setDateOfPublishing(Date dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
    }


    @Override
    public String toString() {
        return "BookDto:" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", dateOfPublishing=" + dateOfPublishing;
    }
}
