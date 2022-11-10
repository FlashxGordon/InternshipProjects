package com.sa22.LMA.library_management.book_management.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    public int id;
    public String name;

    public AuthorDTO(String name) {
        this.name = name;
    }
}
