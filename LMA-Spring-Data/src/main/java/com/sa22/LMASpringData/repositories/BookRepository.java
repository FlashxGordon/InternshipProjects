package com.sa22.LMASpringData.repositories;

import com.sa22.LMASpringData.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

}
