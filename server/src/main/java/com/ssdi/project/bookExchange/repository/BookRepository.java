package com.ssdi.project.bookExchange.repository;

import com.ssdi.project.bookExchange.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
