package com.ssdi.project.bookExchange.controller;

import com.ssdi.project.bookExchange.Vo.MessageVo;
import com.ssdi.project.bookExchange.model.Message;
import com.ssdi.project.bookExchange.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/user/{userId}/sendMessage")
    public HashMap<String,Object> saveMessage(@PathVariable(value = "userId") Integer userId, @RequestBody Message message){
        HashMap<String,Object> hm = new HashMap<>();
        Message sentMessage = messageService.saveMessage(message);
        hm.put("message","Message has been sent successfully");
        hm.put("status", String.valueOf(200));
        hm.put("sentMessage", sentMessage);
        return hm;
    }

    @GetMapping("/getmessages/{userid}")
    public List<MessageVo> getinboxmessages(@PathVariable(value = "userid") int userId) {
    	return messageService.getinboxmessages(userId);
    }
}
