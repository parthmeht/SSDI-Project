package com.ssdi.project.bookExchange.service;

import com.ssdi.project.bookExchange.Vo.MessageVo;
import com.ssdi.project.bookExchange.model.Message;
import com.ssdi.project.bookExchange.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("messageService")
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message){
        message.setCreatedDate(new Timestamp((new Date()).getTime()));
        messageRepository.save(message);
        return message;
    }

    public List<MessageVo> getinboxmessages(int userId) {

        List<Message> messageslist = messageRepository.findAll();
        List<MessageVo> messageslistVo = new ArrayList<MessageVo>();

        for (Message message : messageslist) {

            if(message.getReceiverId().getId() == userId) {

                MessageVo mssageVo = new MessageVo();
                mssageVo.setCreatedDate(message.getCreatedDate());
                mssageVo.setSenderName(message.getSenderId().getName());
                mssageVo.setTitle(message.getTitle());
                mssageVo.setBody(message.getBody());
                messageslistVo.add(mssageVo);
            }
        }

        return messageslistVo;
    }
}
