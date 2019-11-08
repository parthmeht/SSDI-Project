package com.ssdi.project.bookExchange.repository;

import com.ssdi.project.bookExchange.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select u from Book u where title like %?1% or author like %?1%")
    List<Book> searchBooks(String query);
}
