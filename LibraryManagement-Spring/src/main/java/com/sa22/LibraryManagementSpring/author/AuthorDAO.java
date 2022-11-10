package com.sa22.LibraryManagementSpring.author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO<T> {

    List<T> list();

    void create(T t);

    Optional<T> get (int id);

    void update(T t, int id);

    void delete(int id);
}
