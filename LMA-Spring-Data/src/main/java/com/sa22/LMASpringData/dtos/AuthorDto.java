package com.sa22.LMASpringData.dtos;

public class AuthorDto {

    private long id;

    private String authorName;

    public AuthorDto() {
    }

    public AuthorDto(long id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

}

