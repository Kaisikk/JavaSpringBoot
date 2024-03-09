package com.kaisikk.java.springboot.javaspringboot.controller;

import com.kaisikk.java.springboot.javaspringboot.domain.Message;
import com.kaisikk.java.springboot.javaspringboot.domain.User;
import com.kaisikk.java.springboot.javaspringboot.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kaisikk
 *
 * Контроллер для тестов
 */
@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String firstController( Map<String, Object> model){

    // имя html файла (ожидается, что лежит в папке template)
    return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model){

        List<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model){
        Message message = new Message(text, tag);
        messageRepository.save(message);

        List<Message> messages = messageRepository.findAll();
        model.put("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){

        List<Message> messages = new ArrayList<>();

        if(filter != null && !filter.isEmpty()){
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.put("messages", messages);
        return "main";
    }

}
