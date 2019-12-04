package com.ssdi.project.bookExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssdi.project.bookExchange.controller.BookController;
import com.ssdi.project.bookExchange.model.Book;
import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.repository.BookRepository;
import com.ssdi.project.bookExchange.service.BookService;
import com.ssdi.project.bookExchange.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {

    @Mock
    private BookRepository mockBookRepository;

    private BookService bookServiceUnderTest;

    @InjectMocks
    private BookController bookController;
    private User user;
    private Book book;
    private Book book2;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);
        bookServiceUnderTest = new BookService(mockBookRepository);

        List<Book> books = new ArrayList<>();

        this.user = User.builder()
                .id(15)
                .name("User")
                .lastName("McGee")
                .email("user@test234.com")
                .build();

        book = Book.builder().id(1).author("J K Rowling").title("Harry Potter").isListed(true).price(29.99).user(user).build();
        book2 = Book.builder().id(2).author("Test Author").title("Test Book").isListed(true).price(19.99).user(user).build();
        books.add(book);
        books.add(book2);

        Mockito.when(mockBookRepository.save(any()))
                .thenReturn(book);
        Mockito.when(mockBookRepository.findByUserId(anyInt()))
                .thenReturn(books);
        Mockito.when(mockBookRepository.getById(anyInt()))
                .thenReturn(book);
        Mockito.when(mockBookRepository.findAll())
                .thenReturn(books);
    }

    @Test
    public void testSaveBook(){
        final String title = "Harry Potter";
        Book result = bookServiceUnderTest.saveBook(book);
        assertEquals(title, result.getTitle());
    }

    @Test
    public void testGetBookById(){
        final int id = 1;
        Book result = bookServiceUnderTest.getBookById(id);
        assertEquals(id, result.getId());
    }

    @Test
    public void testGetAllBooks(){
        List<Book> bookList = bookServiceUnderTest.findAll();
        System.out.println(bookList.size());
        assertEquals(true,bookList.size()>0);
        assertEquals("Harry Potter",bookList.get(0).getTitle());
        assertEquals("Test Book",bookList.get(1).getTitle());
    }

    @Test
    public void testFindByUserId(){
        final int id = 15;
        List<Book> bookList =  bookServiceUnderTest.getBookByUserId(id);
        assertEquals(true,bookList.size()>0);
        assertEquals("Harry Potter",bookList.get(0).getTitle());
        assertEquals("Test Book",bookList.get(1).getTitle());
    }

}
