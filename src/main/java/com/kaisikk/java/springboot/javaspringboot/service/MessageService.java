package com.kaisikk.java.springboot.javaspringboot.service;

import com.kaisikk.java.springboot.javaspringboot.domain.Message;
import com.kaisikk.java.springboot.javaspringboot.domain.User;
import com.kaisikk.java.springboot.javaspringboot.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public Page<Message> messageList(Pageable pageable, String filter){

        Page<Message> page;

        if (filter != null && !filter.isEmpty()) {
            page = messageRepository.findByTag(filter, pageable);
        } else {
            page = messageRepository.findAll(pageable);
        }

        return page;
    }

    public Page<Message> messageListForUser(Pageable pageable, User currentUser, User author) {

        return messageRepository.findByUser(pageable, author);
    }
}
