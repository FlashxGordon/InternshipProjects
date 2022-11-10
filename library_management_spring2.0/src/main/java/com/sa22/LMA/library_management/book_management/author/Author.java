package com.sa22.LMA.library_management.book_management.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private int id;
    private String name;
    Author(String name) {
        this.name = name;
    }

}