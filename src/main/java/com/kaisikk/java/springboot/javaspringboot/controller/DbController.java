package com.kaisikk.java.springboot.javaspringboot.controller;

import com.kaisikk.java.springboot.javaspringboot.model.entity.Message;
import com.kaisikk.java.springboot.javaspringboot.model.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
public class DbController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/dbController")
    public String createMessage(@RequestParam(name = "name", required=false, defaultValue = "Test") String name){

        Message message = new Message();
        message.setTag("123");
        message.setText(name);

        messageRepository.save(message);

        return null;
    }
}
