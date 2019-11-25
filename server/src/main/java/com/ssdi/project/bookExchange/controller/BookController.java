package com.ssdi.project.bookExchange.controller;

import com.ssdi.project.bookExchange.model.Book;
import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.repository.BookRepository;
import com.ssdi.project.bookExchange.repository.UserRepository;
import com.ssdi.project.bookExchange.service.BookService;
import com.ssdi.project.bookExchange.service.UserService;

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
    private UserService userService;

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookService.findAll();
    }

    @GetMapping("/user/{userId}/books")
    public List<Book> getBooksByUser(@PathVariable(value = "userId") Integer userId) {
        return bookService.getBookByUserId(userId);
    }

    @PostMapping("/user/{userId}/addBook")
    public Book createBook(@PathVariable(value = "userId") Integer userId,
                             @Valid @RequestBody Book book) throws Exception {
    	User user = userService.getUserById(userId);
    	book.setUser(user);
        return bookService.saveBook(book);
 //    	return userRepository.findById(userId).map(user -> {
//            book.setUser(user);
//            return bookRepository.save(book);
//        }).orElseThrow(()-> new Exception("user not found"));
    }

    @PutMapping("/updateBook/{bookId}")
    public Book updateBook(@PathVariable(value = "bookId") int bookId, @Valid @RequestBody Book bookReq)
            throws Exception {
    	Book book = bookService.getBookById(bookId);
    	book.setTitle(bookReq.getTitle());
    	book.setAuthor(bookReq.getAuthor());
    	book.setPrice(bookReq.getPrice());
    	book.setIsListed(bookReq.getIsListed());
    	return bookService.saveBook(book);
    	//    		return bookRepository.findById(bookId).map(book-> {
//            book.setTitle(bookReq.getTitle());
//            book.setAuthor(bookReq.getAuthor());
//            return bookRepository.save(book);
//        }).orElseThrow(()-> new Exception("book id not found"));
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
