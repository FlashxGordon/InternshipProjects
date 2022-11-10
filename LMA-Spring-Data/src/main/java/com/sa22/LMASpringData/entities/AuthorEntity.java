package com.sa22.LMASpringData.entities;

import com.sa22.LMASpringData.dtos.AuthorDto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "author_table")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", unique = true)
    private long authorId;
    @Column(name = "author_name")
    private String authorName;


    public AuthorEntity() {
    }

    public AuthorEntity(String authorName) {
        this.authorName = authorName;
    }

    public AuthorEntity(AuthorDto authorDto) {
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "AuthorEntity{" +
                "authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorEntity)) return false;
        AuthorEntity that = (AuthorEntity) o;
        return authorId == that.authorId && authorName.equals(that.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, authorName);
    }
}
