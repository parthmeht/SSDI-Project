package com.ssdi.project.bookExchange.controller;

import com.ssdi.project.bookExchange.model.Book;
import com.ssdi.project.bookExchange.repository.BookRepository;
import com.ssdi.project.bookExchange.repository.UserRepository;
import com.ssdi.project.bookExchange.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/user/{userId}/books")
    public List<Book> getBooksByUser(@PathVariable(value = "userId") Integer userId) {
        return bookRepository.findByUserId(userId);
    }

    @PostMapping("/user/{userId}/addBook")
    public Book createBook(@PathVariable(value = "userId") Integer userId,
                             @Valid @RequestBody Book book) throws Exception {
        return userRepository.findById(userId).map(user -> {
            book.setUser(user);
            return bookRepository.save(book);
        }).orElseThrow(()-> new Exception("user not found"));
    }

    @PutMapping("/users/{userId}/updateBook/{bookId}")
    public Book updateBook(@PathVariable(value = "userId") Integer userId,
                           @PathVariable(value = "bookId") Integer bookId, @Valid @RequestBody Book bookReq)
            throws Exception {
        if (!userRepository.existsById(userId)) {
            throw new Exception("UserId not found");
        }

        return bookRepository.findById(bookId).map(book-> {
            book.setTitle(bookReq.getTitle());
            return bookRepository.save(book);
        }).orElseThrow(()-> new Exception("book id not found"));
    }

    @GetMapping("book/search")
    @ResponseBody
    public List<Book> bookSearch(@RequestParam String query) {
        List<Book> bookList = new ArrayList<>();
        if(query != null && !query.isEmpty()) {
            bookList = bookService.searchBooks(query);
        }
        return bookList;
    }

}
