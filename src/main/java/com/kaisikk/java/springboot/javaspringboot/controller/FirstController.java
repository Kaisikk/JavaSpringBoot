package com.kaisikk.java.springboot.javaspringboot.controller;

import com.kaisikk.java.springboot.javaspringboot.domain.Message;
import com.kaisikk.java.springboot.javaspringboot.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FirstController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greeting")
    public String firstController(@RequestParam(name = "name", required = false, defaultValue = "Kaisik") String name, Map<String, Object> model){

        // модель - куда складываем данные которые хотим вернуть
    model.put("name", name);

    // имя html файла (ожидается, что лежит в папке template)
    return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model){

        List<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
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
