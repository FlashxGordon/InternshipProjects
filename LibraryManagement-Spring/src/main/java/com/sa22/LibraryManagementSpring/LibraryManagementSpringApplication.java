package com.sa22.LibraryManagementSpring;

import com.sa22.LibraryManagementSpring.author.Author;
import com.sa22.LibraryManagementSpring.author.AuthorDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LibraryManagementSpringApplication {

    private static AuthorDAO<Author> dao;

    public LibraryManagementSpringApplication(AuthorDAO<Author> dao) {
        this.dao = dao;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSpringApplication.class, args);

        System.out.println("All authors ---------------");
        List<Author> authors = dao.list();
        authors.forEach(System.out::println);
    }
}