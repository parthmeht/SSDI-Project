package com.ssdi.project.bookExchange;

import com.ssdi.project.bookExchange.model.Message;
import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.repository.MessageRepository;
import com.ssdi.project.bookExchange.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {
    @Mock
    private MessageRepository mockMessageRepository;
    private MessageService messageServiceUnderTest;
    private Message message;

    @Mock
    User testUser;
    
    @Mock
    User testUser2;
    
    @Mock
    Message testMessage;
    
    @Before
    public void setUp() {
        initMocks(this);
        messageServiceUnderTest = new MessageService(mockMessageRepository);
        User user = User.builder()
                .id(1)
                .name("Gustavo")
                .lastName("Ponce")
                .email("test@test.com")
                .build();
        User user1 = User.builder()
                .id(2)
                .name("Parth")
                .lastName("Mehta")
                .email("testParth@test.com")
                .build();

        //	Do you want to set receiver id here ? Have set sender Id twice
        message = Message.builder()
                .id(1)
                .title("Buy Book")
                .body("Hello, I would like to buy your Harry Potter Book")
                .senderId(user)
                .senderId(user1)
                .createdDate(new Timestamp((new Date()).getTime()))
                .build();
        Mockito.when(mockMessageRepository.save(any()))
                .thenReturn(message);
    }

    @Test
    public void testSentMessage() {
        final String title = "Buy Book";
        final Message result = messageServiceUnderTest.saveMessage(message);
        assertEquals(title, result.getTitle());
    }
    
    @Test
    public void testGetInboxMessages() {
    	
    	List<Message> messageslist = new ArrayList<Message>();
    	
    	Mockito.when(testMessage.getCreatedDate()).thenReturn(new Timestamp((new Date()).getTime()));
    	Mockito.when(testMessage.getSenderId()).thenReturn(testUser2);
    	Mockito.when(testUser2.getName()).thenReturn("Koushik");
    	Mockito.when(testMessage.getTitle()).thenReturn("Buy Book");
    	Mockito.when(testMessage.getBody()).thenReturn("Hello, I would like to buy your Harry Potter Book");
  
    	messageslist.add(testMessage);
    	Mockito.when(testMessage.getReceiverId()).thenReturn(testUser);
    	Mockito.when(testUser.getId()).thenReturn(1);
    	Mockito.when(mockMessageRepository.findAll()).thenReturn(messageslist);
    	assertEquals("Buy Book", messageServiceUnderTest.getinboxmessages(1).get(0).getTitle());
    }
    
    
}
