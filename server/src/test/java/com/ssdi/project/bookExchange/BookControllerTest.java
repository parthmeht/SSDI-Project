package com.ssdi.project.bookExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssdi.project.bookExchange.controller.BookController;
import com.ssdi.project.bookExchange.model.Book;
import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {
    @Mock
    private BookService mockBookService;
    @InjectMocks
    private BookController bookController;
    private User user;
    private Book book;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        try {
            initMocks(this);
            this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
            this.user = User.builder()
                    .id(15)
                    .name("User")
                    .lastName("McGee")
                    .email("user@test234.com")
                    .build();

            book = Book.builder().id(1).author("J K Rowling").title("Harry Potter").isListed(true).price(29.99).user(user).build();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testSaveBook(){
        try {
            mockMvc.perform(post("/user/15/addBook")
                    .content(ToJSONString(book))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testUpdateBook(){
        try {
            Book book2 = Book.builder().id(1).author("J K Rowling").title("Harry Potter 2").isListed(true).price(25.99).user(user).build();
            mockMvc.perform(put("/updateBook/15")
                    .content(ToJSONString(book))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testGetAllBooks(){
        try {
            mockMvc.perform(get("/books"))
                    .andExpect(status().isOk());
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testBooksByUser(){
        try {
            mockMvc.perform(get("/user/15/books"))
                    .andExpect(status().isOk());
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testSearchBooks(){
        try {
            mockMvc.perform(get("/book/search?query=book"))
                    .andExpect(status().isOk());
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static String ToJSONString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
