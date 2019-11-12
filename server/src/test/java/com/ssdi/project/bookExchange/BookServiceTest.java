package com.ssdi.project.bookExchange;

import com.ssdi.project.bookExchange.model.Book;
import com.ssdi.project.bookExchange.repository.BookRepository;
import com.ssdi.project.bookExchange.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
    @Mock
    private BookRepository mockBookRepository;

    private BookService bookServiceUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        bookServiceUnderTest = new BookService(mockBookRepository);
        List<Book> books = new ArrayList<>();
        List<Book> searchResults = new ArrayList<>();
        Book book1 = Book.builder().id(1).author("J K Rowling").title("Harry Potter").isListed(true).price(25.99).build();
        Book book2 = Book.builder().id(1).author("Joli Jensen").title("Write No Matter What: Advice for Academics").isListed(true).price(29.99).build();
        Book book3 = Book.builder().id(1).author("Thomas H. Cormen, Charles E. Leiserson, Ronald Rivest, Clifford Stein").title("Introduction to Algorithms").isListed(false).price(99.99).build();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        searchResults.add(book1);
        Mockito.when(mockBookRepository.findAll())
                .thenReturn(books);
        Mockito.when(mockBookRepository.searchBooks("Harry Potter"))
                .thenReturn(searchResults);
    }

    @Test
    public void testListAllBooks(){
        List<Book> books = bookServiceUnderTest.findAll();
        assertEquals(true,books.size()>0);
        assertEquals("Harry Potter",books.get(0).getTitle());
        assertEquals("Write No Matter What: Advice for Academics",books.get(1).getTitle());
        assertEquals("Introduction to Algorithms",books.get(2).getTitle());

        assertNotEquals(99.99, books.get(1).getPrice());
        assertNotEquals(true, books.get(2).getIsListed());
    }

    @Test
    public void testBookSearch() {
        // Setup
        final String title = "Harry Potter";

        // Run the test
        final List<Book> result = bookServiceUnderTest.searchBooks(title);

        // Verify the results
        assertEquals(title, result.get(0).getTitle());
    }

}
