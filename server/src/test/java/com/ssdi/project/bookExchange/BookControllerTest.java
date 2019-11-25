package com.ssdi.project.bookExchange;

import com.ssdi.project.bookExchange.model.Book;
import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.repository.BookRepository;
import com.ssdi.project.bookExchange.service.BookService;
import com.ssdi.project.bookExchange.controller.BookController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.repository.RoleRepository;
import com.ssdi.project.bookExchange.repository.UserRepository;
import com.ssdi.project.bookExchange.service.UserService;
import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {
    @Mock
    private BookService bookServiceUnderTest;
    private BookRepository mockBookRepository;
    private User user;
    private Book book;
    private Book bookTest;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Mock
    private BookController bookController;

    @Before
    public void setUp() {
        try {
            initMocks(this);
            this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
            bookServiceUnderTest = new BookService(mockBookRepository);
            User user = User.builder()
                    .id(1)
                    .name("User")
                    .lastName("McGee")
                    .email("user@test234.com")
                    .build();

            book = Book.builder().id(1).author("J K Rowling").title("Harry Potter").isListed(true).price(25.99).user(user).build();
            bookController.createBook(1, book);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testSaveBook(){
        try {
            mockMvc.perform(post("/user/1/addBook")
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
            mockMvc.perform(put("/updateBook/1")
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
            mockMvc.perform(get("/user/1/books"))
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
