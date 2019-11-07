package com.ssdi.project.bookExchange.controller;

import com.ssdi.project.bookExchange.model.Book;
import com.ssdi.project.bookExchange.repository.BookRepository;
import com.ssdi.project.bookExchange.repository.UserRepository;
import com.ssdi.project.bookExchange.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/book")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class NovelBookController {
    @Autowired
    private BookRepository bookRepository;
    private BookService bookService;

    @PostMapping(path="/add-book")
    public void addNewBooks (@RequestBody Book book) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        long millis=System.currentTimeMillis();
        System.out.println(book.toString());
        //Book book = new Book(book_title,book_author,new Date(millis));
        book.setDate(new Date(millis));
        bookRepository.save(book);
    }

    @GetMapping(path="/allBooks")
    public List<Book> getAllUsers() {
        // This returns a JSON or XML with the users
        return (List<Book>) bookRepository.findAll();
    }

    @GetMapping("/search/{query}")
    public List<Book> bookSearch(@PathVariable Optional<String> query) {
        List<Book> bookList = bookService.searchBooks(query.orElse(" "));
        return bookList;
    }
}
