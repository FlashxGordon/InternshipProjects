package com.sa22.LMASpringData.entities;

import com.sa22.LMASpringData.dtos.BookDto;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "book_table")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long bookId;
    @Column(name = "book_name")
    private String bookName;
    @Column(name = "author_name")
    private String authorName;
    @Column(name = "date_of_publishing")
    private Date dateOfPublishing;


    public BookEntity() {
    }

    public BookEntity(BookDto bookDto) {

    }

    public BookEntity(String bookName, String authorName, Date dateOfPublishing) {
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

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDateOfPublishing() {
        return dateOfPublishing;
    }

    public void setDateOfPublishing(Date dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", dateOfPublishing=" + dateOfPublishing +
                '}';
    }
}
