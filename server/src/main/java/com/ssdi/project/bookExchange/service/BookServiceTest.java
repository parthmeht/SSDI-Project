package com.ssdi.project.bookExchange.service;

import com.ssdi.project.bookExchange.model.Book;
import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.MockitoAnnotations.initMocks;

public class BookServiceTest {
    @Mock
    private BookRepository mockBookRepository;

    private BookService bookServiceUnderTest;
    private Book bookA;
    private Book bookB;
    private User user;
    private List<Book> bookList;

    @Before
    public void setUp() {
        initMocks(this);
        bookServiceUnderTest = new BookService(mockBookRepository);

        user = User.builder()
                .id(1)
                .name("Gustavo")
                .lastName("Ponce")
                .email("test@test.com")
                .build();
        bookA = Book.builder()
                .id(1)
                .title("Harry Potter and the Sorcerers Stone")
                .author("JK Rowling")
                .price(10.00)
                .isListed(true)
                .user(user)
                .build();

        bookA = Book.builder()
                .id(2)
                .title("The Lorax")
                .author("Dr Seuss")
                .price(8.00)
                .isListed(true)
                .user(user)
                .build();

        bookList.add(bookA);

        Mockito.when(mockBookRepository.save(any()))
                .thenReturn(bookA);
        Mockito.when(mockBookRepository.findByUserId(anyInt()))
                .thenReturn(bookList);
        Mockito.when(mockBookRepository.searchBooks("The Lorax"))
                .thenReturn(bookList);
    }

    @Test
    public void testBookSearch() {
        // Setup
        final String title = "The Lorax";

        // Run the test
        final List<Book> result = bookServiceUnderTest.searchBooks(title);

        // Verify the results
        assertEquals(title, result.get(0).getTitle());
    }

//    @Test
//    public void testFindByUserId() {
//        // Setup
//        final int length = 2;
//        final int id = 1;
//
//        // Run the test
//        final List<Book> result = bookServiceUnderTest.findByUserId(id);
//
//        // Verify the results
//        assertEquals(length, result.length());
//    }

//    @Test
//    public void testSaveBook() {
//        // Setup
//        final String author = "JK Rowling";
//
//        // Run the test
//        User result = bookServiceUnderTest.saveBook(Book.builder().build());
//
//        // Verify the results
//        assertEquals(author, result.getEmail());
//    }
}
