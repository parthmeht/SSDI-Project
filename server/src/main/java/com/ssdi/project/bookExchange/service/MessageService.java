package com.ssdi.project.bookExchange.service;

import com.ssdi.project.bookExchange.model.Message;
import com.ssdi.project.bookExchange.repository.MessageRepository;
import com.ssdi.project.bookExchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service("messageService")
public class MessageService {
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(@Qualifier("userRepository") UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public void saveMessage(Message message){
        message.setCreatedDate(new Timestamp((new Date()).getTime()));
        messageRepository.save(message);
    }
}
