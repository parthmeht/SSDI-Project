package com.ssdi.project.bookExchange.service;

import com.ssdi.project.bookExchange.model.Book;
import com.ssdi.project.bookExchange.repository.BookRepository;
import com.ssdi.project.bookExchange.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.validation.Valid;

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

    public List<Book> findAll(){
        return bookRepository.findAll();
    }
    
    public Book saveBook(Book book) {
    	bookRepository.save(book);
		return book;
    }
    
    public Book getBookById(int id) {
    	return bookRepository.getById(id);
    }
    
    public List<Book>  getBookByUserId(int id) {
        return bookRepository.findByUserId(id);
    }

	public void updateBook(int bookId, @Valid Book bookReq) {
		// TODO Auto-generated method stub
		List<Book> books = bookRepository.findAll();
		for(int i=0;i<books.size();i++) {
			Book book = books.get(i);
			if(book.getId() == bookId) {
				bookRepository.save(bookReq);
			}
		}
		}
}
