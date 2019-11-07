package com.ssdi.project.bookExchange.service;

import com.ssdi.project.bookExchange.model.Book;
import com.ssdi.project.bookExchange.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("bookService")
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(@Qualifier("bookRepository") BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }

}
