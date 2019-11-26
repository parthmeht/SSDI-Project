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
import java.util.Date;

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
}
