package com.kaisikk.java.springboot.javaspringboot.controller;

import com.kaisikk.java.springboot.javaspringboot.domain.Role;
import com.kaisikk.java.springboot.javaspringboot.domain.User;
import com.kaisikk.java.springboot.javaspringboot.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class Registration {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model){

        User currentUser = userRepository.findByUsername(user.getUsername());

        if(currentUser != null){
    model.put("message", "User is exists!");
        return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

}
