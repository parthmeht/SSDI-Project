package com.ssdi.project.bookExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssdi.project.bookExchange.controller.MessageController;
import com.ssdi.project.bookExchange.model.Message;
import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MessageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MessageService mockMessageService;
    @InjectMocks
    private MessageController messageController;

    Message message;
    User user, user1;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
        user = User.builder()
                .id(1)
                .name("Gustavo")
                .lastName("Ponce")
                .email("test@test.com")
                .password("password")
                .build();
        user1 = User.builder()
                .id(2)
                .name("Parth")
                .lastName("Mehta")
                .email("testParth@test.com")
                .password("password")
                .build();

        message = Message.builder()
                .id(1)
                .title("Buy Book")
                .body("Hello, I would like to buy your Harry Potter Book")
                .senderId(user)
                .senderId(user1)
                .createdDate(new Timestamp((new Date()).getTime()))
                .build();
        Mockito.when(mockMessageService.saveMessage(any()))
                .thenReturn(message);
    }

    @Test
    public void sendMessageAPITest() throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                .post("/user/12/sendMessage")
                .content(asJsonString(message))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists());
    }
    
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
